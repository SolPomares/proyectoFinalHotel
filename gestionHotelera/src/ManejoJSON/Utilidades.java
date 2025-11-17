package ManejoJSON;

import Clases.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import Enums.TipoDisponibilidad;
import Enums.TipoHabitacion;
import Enums.TipoRol;
import Enums.Turno;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Utilidades {

   //CARGAR Y MAPEAR HABITACIONES

    public static List<Habitacion> cargarHabitaciones() throws JSONException {
    List<Habitacion> habitaciones = new ArrayList<>();

    JSONTokener tokener = JSONUtiles.leer("habitaciones.json");
    if (tokener != null) {
        JSONObject jsonPrincipal = new JSONObject(tokener);
        JSONArray arrayHabitaciones = jsonPrincipal.getJSONArray("habitaciones");

        for (int i = 0; i < arrayHabitaciones.length(); i++) {
            JSONObject habitacionJSON = arrayHabitaciones.getJSONObject(i);

            int numeroHabitacion = habitacionJSON.getInt("numeroHabitacion");
            double valorDiario = habitacionJSON.getDouble("valorDiario");
            String tipoHabitacionStr = habitacionJSON.getString("tipoHabitacion");
            String disponibilidadStr = habitacionJSON.getString("disponibilidad");
            int capacidad = habitacionJSON.getInt("capacidad");

            // CONVERTIR String → Date
            Date dateIn = null;
            Date dateOut = null;

            if (!habitacionJSON.isNull("dateIn")) {
                String dateInStr = habitacionJSON.getString("dateIn");
                if (!"null".equals(dateInStr) && !dateInStr.trim().isEmpty()) {
                    try {
                        // Formato: "2024-01-15T14:00:00" o "2024-01-15"
                        if (dateInStr.contains("T")) {
                            // Formato ISO con tiempo
                            dateIn = java.sql.Timestamp.valueOf(dateInStr.replace("T", " "));
                        } else {
                            // Formato solo fecha
                            dateIn = java.sql.Date.valueOf(dateInStr);
                        }
                    } catch (Exception e) {
                        System.out.println("Error parseando dateIn: " + dateInStr);
                    }
                }
            }

            if (!habitacionJSON.isNull("dateOut")) {
                String dateOutStr = habitacionJSON.getString("dateOut");
                if (!"null".equals(dateOutStr) && !dateOutStr.trim().isEmpty()) {
                    try {
                        if (dateOutStr.contains("T")) {
                            dateOut = java.sql.Timestamp.valueOf(dateOutStr.replace("T", " "));
                        } else {
                            dateOut = java.sql.Date.valueOf(dateOutStr);
                        }
                    } catch (Exception e) {
                        System.out.println("Error parseando dateOut: " + dateOutStr);
                    }
                }
            }

            TipoHabitacion tipoHabitacion = TipoHabitacion.valueOf(tipoHabitacionStr);
            TipoDisponibilidad disponibilidad = TipoDisponibilidad.valueOf(disponibilidadStr);

            Habitacion habitacion = new Habitacion(
                    numeroHabitacion,
                    valorDiario,
                    tipoHabitacion,
                    disponibilidad,
                    null,  // tipoNODisponible
                    capacidad,
                    dateIn,   // ← Ahora es Date
                    dateOut   // ← Ahora es Date
            );

            habitaciones.add(habitacion);
        }
        System.out.println("✅ " + habitaciones.size() + " habitaciones cargadas");
    } else {
        System.out.println("❌ No se pudieron cargar las habitaciones");
    }

    return habitaciones;
}


    // CARGAR Y MAPEAR USUARIOS
    public static List<Usuario> cargarUsuarios() throws JSONException {
        List<Usuario> usuarios = new ArrayList<>();

        JSONTokener tokener = JSONUtiles.leer("usuarios.json");
        if (tokener != null) {
            JSONObject jsonPrincipal = new JSONObject(tokener);
            JSONArray arrayUsuarios = jsonPrincipal.getJSONArray("usuarios");

            for (int i = 0; i < arrayUsuarios.length(); i++) {
                JSONObject usuarioJSON = arrayUsuarios.getJSONObject(i);
                String tipoUsuario = usuarioJSON.getString("tipoUsuario");

                Usuario usuario = null;

                switch (tipoUsuario) {
                    case "Administrador":
                        usuario = mapearAdministrador(usuarioJSON);
                        break;
                    case "Recepcionista":
                        usuario = mapearRecepcionista(usuarioJSON);
                        break;
                    case "Pasajero":
                        usuario = mapearPasajero(usuarioJSON);
                        break;
                    case "Empleados":
                        usuario = mapearEmpleado(usuarioJSON);
                        break;
                }

                if (usuario != null) {
                    usuarios.add(usuario);
                }
            }
            System.out.println("✅ " + usuarios.size() + " usuarios cargados");
        } else {
            System.out.println("❌ No se pudieron cargar los usuarios");
        }

        return usuarios;
    }

    // MAPEAR ADMINISTRADOR
    private static Administrador mapearAdministrador(JSONObject json) throws JSONException {
        return new Administrador(
                UUID.fromString(json.getString("idUsuario")),
                json.getString("nombre"),
                json.getString("apellido"),
                json.getInt("dni"),
                TipoRol.valueOf(json.getString("tipoRol")),
                json.getString("nombreUsuario"),
                json.getString("email"),
                json.getString("claveAdministracion"),
                json.getBoolean("acceso")
        );
    }

    // MAPEAR RECEPCIONISTA
    private static Recepcionista mapearRecepcionista(JSONObject json) throws JSONException {
        return new Recepcionista(
                UUID.fromString(json.getString("idUsuario")),
                json.getString("nombre"),
                json.getString("apellido"),
                json.getInt("dni"),
                TipoRol.valueOf(json.getString("tipoRol")),
                json.getString("nombreUsuario"),
                json.getString("email"),
                json.getBoolean("acceso"),        // ← acceso va ANTES de turno
                Turno.valueOf(json.getString("turno")),
                json.getString("claveRecepcion"), // ← clave va DESPUÉS de turno
                null                              // ← gestorHabitaciones (puede ser null por ahora)
        );
    }

    // MAPEAR PASAJERO
    private static Pasajero mapearPasajero(JSONObject json) throws JSONException {
        // MAPEAR ARRAY DE STRINGS (historial)
        List<String> historial = new ArrayList<>();
        JSONArray historialArray = json.getJSONArray("historial");
        for (int i = 0; i < historialArray.length(); i++) {
            historial.add(historialArray.getString(i));
        }

        return new Pasajero(
                UUID.fromString(json.getString("idUsuario")),
                json.getString("nombre"),
                json.getString("apellido"),
                json.getInt("dni"),
                TipoRol.valueOf(json.getString("tipoRol")),
                json.getString("origen"),
                json.getString("domicilioOrigen"),
                historial,
                json.getString("nombreUsuario"),  // ← PARÁMETRO QUE FALTABA
                json.getString("clavePasajero")   // ← PARÁMETRO QUE FALTABA
        );

    }

    // MAPEAR EMPLEADO GENÉRICO
    private static Empleados mapearEmpleado(JSONObject json) throws JSONException {
        return new Empleados(
                UUID.fromString(json.getString("idUsuario")),
                json.getString("nombre"),
                json.getString("apellido"),
                json.getInt("dni"),
                TipoRol.valueOf(json.getString("tipoRol")),
                json.getString("nombreUsuario"),
                json.getString("email"),
                json.getBoolean("acceso")
        ) {
            @Override
            public void imprimirDatos() {
                System.out.println("Empleado: " + getNombre() + " " + getApellido());
            }
        };
    }
}//}