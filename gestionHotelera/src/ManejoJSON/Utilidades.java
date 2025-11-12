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
import java.util.List;

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

                String dateIn = null;
                String dateOut = null;

                if (!habitacionJSON.isNull("dateIn")) {
                    dateIn = habitacionJSON.getString("dateIn");
                    if ("null".equals(dateIn)) dateIn = null;
                }

                if (!habitacionJSON.isNull("dateOut")) {
                    dateOut = habitacionJSON.getString("dateOut");
                    if ("null".equals(dateOut)) dateOut = null;
                }

                TipoHabitacion tipoHabitacion = TipoHabitacion.valueOf(tipoHabitacionStr);
                TipoDisponibilidad disponibilidad = TipoDisponibilidad.valueOf(disponibilidadStr);

                Habitacion habitacion = new Habitacion(
                        numeroHabitacion,
                        valorDiario,
                        tipoHabitacion,
                        disponibilidad,
                        capacidad,
                        dateIn,
                        dateOut
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
                json.getInt("idUsuario"),
                json.getString("nombre"),
                json.getString("apellido"),
                json.getInt("dni"),
                TipoRol.valueOf(json.getString("tipoRol")),
                json.getString("nombreUsuario"),
                json.getString("email"),
                json.getBoolean("acceso"),
                json.getString("claveAdministracion")
        );
    }

    // MAPEAR RECEPCIONISTA
    private static Recepcionista mapearRecepcionista(JSONObject json) throws JSONException {
        return new Recepcionista(
                json.getInt("idUsuario"),
                json.getString("nombre"),
                json.getString("apellido"),
                json.getInt("dni"),
                TipoRol.valueOf(json.getString("tipoRol")),
                json.getString("nombreUsuario"),
                json.getString("email"),
                json.getBoolean("acceso"),
                Turno.valueOf(json.getString("turno"))
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
                json.getInt("idUsuario"),
                json.getString("nombre"),
                json.getString("apellido"),
                json.getInt("dni"),
                TipoRol.valueOf(json.getString("tipoRol")),
                json.getString("origen"),
                json.getString("domicilioOrigen"),
                historial
        );
    }

    // MAPEAR EMPLEADO GENÉRICO
    private static Empleados mapearEmpleado(JSONObject json) throws JSONException {
        return new Empleados(
                json.getInt("idUsuario"),
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