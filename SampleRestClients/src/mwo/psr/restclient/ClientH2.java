package mwo.psr.restclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



public class ClientH2 
{
	
	public static void main(String[] args) 
	{
		ClientH2 c = new ClientH2();
		
		c.interactiveUI(c);		
	}

	
	public void interactiveUI(ClientH2 client)
	{
		String line = null;
        java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in)); 
        
        do
        {
            try
            {
                System.out.print("==> ");
                System.out.flush();
                line = in.readLine();
                if(line == null)
                {
                    break;
                }
                if(line.equals("getUser"))
                {
                    client.getUserJ();
                }
                else if(line.equals("x"))
                {
                }
                else
                {
                    System.out.println("unknown command `" + line + "'");
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        while(!line.equals("x"));
	}


	
	public void getUserJ()
	{
		System.out.println("\n\n*** Starting test case '" + Thread.currentThread().getStackTrace()[1].getMethodName() +"'...");

		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			
			HttpGet getRequest = new HttpGet("http://localhost:8080/SampleRestService/rest/UserManagementService/usersj/2");
			getRequest.addHeader("accept", "application/json");

			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String output;
			StringBuffer sb = new StringBuffer();
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				sb.append(output);
			}

			httpClient.close();

			ObjectMapper mapper = new ObjectMapper();
			try {
				User u = mapper.readValue(sb.toString(), User.class);
				System.out.println(u);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}
		System.out.println("\n\n*** Test case '" + Thread.currentThread().getStackTrace()[1].getMethodName() +"' finished");
	}

	
}
