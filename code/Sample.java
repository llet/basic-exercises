package myapi;

import java.io.IOException;

import org.json.JSONObject;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.util.Util;

public class Sample {
	// 设置APPID/AK/SK	// 设置APPID/AK/SK
	String[] headers = { "Content-Type", "application/json" };
	public static final String URL = "http://vop.baidu.com/server_api";
	public static final String local = "http://localhost";

	public static final String APP_ID = "11664912";
	public static final String API_KEY = "LXcmGzgbQGS5XYoHNTxRveRL";
	public static final String SECRET_KEY = "upBiY3Nquuwq0EOV94tGbPy9NG1VWPmp";

	public static void main(String[] args) throws IOException {
		AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
		client.setConnectionTimeoutInMillis(2000);
		client.setSocketTimeoutInMillis(60000);
	    String path =  "w2.wav";
	    byte[] bs = Util.readFileByBytes(path);
	    int batchLen=1920000;
	    int batch = (int) Math.ceil(((double)bs.length/batchLen));
	    for(int i=0;i<batch;i++) {
	    	int len = Math.min(batchLen, bs.length-i*batchLen-70);
	    	byte[] copy= new byte[len];
	    	int index = i*batchLen;
		    System.arraycopy(bs, 70+index, copy,0, len);
			JSONObject res = client.asr(copy, "pcm", 16000, null);
			System.out.println(res.get("result"));
	    }
	}

	/**
	 * 原始 PCM 的录音参数必须符合 8k/16k 采样率、16bit 位深、单声道，支持的格式有：pcm（不压缩）、wav（不压缩，pcm编码）、amr（压缩格式）。
	 * @throws IOException
	 */
	private static void sdk() throws IOException {
		
	    
        
	}
	
	/**wav 与 pcm 互转
	 * https://blog.csdn.net/zjm750617105/article/details/71698174?locationNum=14&fps=1
	 */
}