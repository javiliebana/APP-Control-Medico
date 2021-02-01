package controllers;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import modelos.User;

public class JsonUtils {
	
	static String sFile = "BBDD.json";

	private static void serializarArrayAJson(ArrayList<User> lista_usuarios) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			FileWriter writer = new FileWriter(sFile);
			gson.toJson(lista_usuarios, writer);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO: handle exception
		}
	}

	public static ArrayList<User> desserializarJsonAArray() {
		ArrayList<User> usuarios = new ArrayList<User>();

		try (Reader reader = new FileReader(sFile)) {
			Gson gson = new Gson();
			Type tipoListaUsuarios = new TypeToken<ArrayList<User>>() {
			}.getType();
			usuarios = gson.fromJson(reader, tipoListaUsuarios);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return usuarios;
	}

}
