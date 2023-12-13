package bus;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileManagerManagers {
	// public static methods: write/read
	private static String filePath = "src//data//managers.ser";

	public static void serialize(ArrayList<Manager> listFromConsole) throws IOException {
		// Write into file : Serialize
		FileOutputStream fileOutputStream = new FileOutputStream(filePath);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(listFromConsole);
		objectOutputStream.close();
		
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Manager> deserialize() throws IOException, ClassNotFoundException {
		// Read from file : Deserialize
		ArrayList<Manager> listFromFile = new ArrayList<Manager>();
		FileInputStream fileInputStream = new FileInputStream(filePath);
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		listFromFile = (ArrayList<Manager>) objectInputStream.readObject();
		objectInputStream.close();

		return listFromFile;
	}
}
