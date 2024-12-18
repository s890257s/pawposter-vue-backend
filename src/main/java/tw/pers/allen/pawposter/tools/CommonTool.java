package tw.pers.allen.pawposter.tools;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Base64;

public class CommonTool {

	public static String convertByteArrayToBase64String(byte[] data) throws IOException {

		ByteArrayInputStream bais = new ByteArrayInputStream(data);

		String mimeType = URLConnection.guessContentTypeFromStream(bais);

		bais.close();

		String base64String = Base64.getEncoder().encodeToString(data);

		return "data:%s;base64,%s".formatted(mimeType, base64String);
	}
}
