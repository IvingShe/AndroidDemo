package com.neusoft.track.http;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import com.neusoft.track.base.TrackBase;
import com.neusoft.track.utils.NLog;

public class PostFile {

	private static final String TAG = "PostFile";
	public static final String POST_URL = TrackBase.baseURL +"ubiquitous.datacollector.web/execute.action";

	public static boolean doPostFile(String filePath) {
//		CloseableHttpClient httpClient = HttpClients.createDefault(); 
		NLog.d(TAG, "POST_URL: " + POST_URL);
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse;
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6*1000); 
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 12*1000);
			HttpPost httpPost = new HttpPost(POST_URL);
			FileBody file = new FileBody(new File(filePath));
			try
			{
				MultipartEntity httpEntity = new MultipartEntity();
			try
			{
				httpEntity.addPart("upload",file);
				httpPost.setEntity(httpEntity);
			}catch(NoSuchMethodError e)
			{
				e.printStackTrace();
				NLog.i(TrackBase.TAG, "send cache file failed! exception : " + e.toString());
			}			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			catch(NoSuchMethodError e)
			{
				e.printStackTrace();
			}
			//		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
	//		builder.addPart("upload", file);
	//		HttpEntity httpEntity = builder.build();			
			httpResponse = httpClient.execute(httpPost);
			int stateCode = httpResponse.getStatusLine().getStatusCode();
			if(stateCode == 200)
			{
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
			NLog.i(TrackBase.TAG, "send cache file failed! exception : " + e.toString());
		}
		catch(NoSuchMethodError e)
		{
			e.printStackTrace();
		}
		return false;
	}

}
