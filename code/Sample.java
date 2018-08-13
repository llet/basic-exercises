
import java.io.IOException;

import org.json.JSONObject;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.util.Util;

public class Sample {
	// 设置APPID/AK/SK
	String[] headers = { "Content-Type", "application/json" };
	public static final String URL = "http://vop.baidu.com/server_api";
	public static final String local = "http://localhost";

	public static final String APP_ID = "11664912";
	public static final String API_KEY = "LXcmGzgbQGS5XYoHNTxRveRL";
	public static final String SECRET_KEY = "upBiY3Nquuwq0EOV94tGbPy9NG1VWPmp";

	public static void main(String[] args) throws IOException {
		sdk() ;
	}

	/**
	 * 原始 PCM 的录音参数必须符合 8k/16k 采样率、16bit 位深、单声道，支持的格式有：pcm（不压缩）、wav（不压缩，pcm编码）、amr（压缩格式）。
	 * @throws IOException
	 */
	private static void sdk() throws IOException {
		AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
		client.setConnectionTimeoutInMillis(2000);
		client.setSocketTimeoutInMillis(60000);
	    String path =  "E:\\sp\\myapi\\myapi\\src\\main\\resources\\16k.pcm";
		JSONObject res = client.asr(Util.readFileByBytes(path), "pcm", 16000, null);
		System.out.println(res.toString(2));
	}
	
	/**wav 与 pcm 互转
	 * https://blog.csdn.net/zjm750617105/article/details/71698174?locationNum=14&fps=1
	 */
}
