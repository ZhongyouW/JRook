import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerTest {
	public static void main(String[] args) throws IOException {
		ProcessBuilder pb = new ProcessBuilder("node", "deez.js");
		pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
		pb.redirectError(ProcessBuilder.Redirect.INHERIT);
		Process p = pb.start();
		System.out.println("SUS");
		// URL url = new URL("/");
		// HttpURLConnection http = (HttpURLConnection)url.openConnection();
		// http.setRequestProperty("Accept", "*/*");

		// System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
		// http.disconnect();
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				System.out.println("In shutdown hook");
				p.destroy();
			}
		}, "Shutdown-thread"));
		
	}
}
