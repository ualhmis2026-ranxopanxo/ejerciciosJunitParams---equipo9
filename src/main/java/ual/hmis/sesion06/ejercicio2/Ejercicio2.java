package ual.hmis.sesion06.ejercicio2;

public class Ejercicio2 {

	public boolean login(String username, String password) {
		if (username == null || password == null) {
			return false;
		}

		if (username.isEmpty() || password.isEmpty()) {
			return false;
		}

		if (username.length() >= 30 || password.length() >= 30) {
			return false;
		}

		if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$")) {
			return false;
		}

		return compruebaLoginEnBD(username, password);
	}

	public boolean compruebaLoginEnBD(String username, String password) {
		return username.equals("user") && password.equals("Pass1");
	}
}