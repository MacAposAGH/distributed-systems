// Kod klasy utworzony na podstawie tutoriala: http://www.tutorialspoint.com/restful/restful_first_application.htm

package mwo.psr.restclient;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import ttss.pl.Schedule;
import ttss.pl.Stop;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

public class ClientJ2 {

	private Client client;
	private static final String SUCCESS_RESULT="<result>success</result>";
	private static final String PASS = "pass";
	private static final String FAIL = "fail";
	
	public static void main(String[] args) 
	{
		ClientJ2 c = new ClientJ2();
		c.init();
		c.interactiveUI(c);
	}

	public void interactiveUI(ClientJ2 client)
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
                else if(line.equals("getUser"))
                {
                    client.getUser();
                }
                else if(line.equals("raw"))
                {
                    client.getRawJson();
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

	private void init(){
		client = ClientBuilder.newClient();
		//client.register(new Authenticator("pum", "pum123"));
		client.register(JacksonJsonProvider.class);
	}


	public void getRawJson()
	{
		System.out.println("\n\n*** Starting test case '" + Thread.currentThread().getStackTrace()[1].getMethodName() +"'...");

		String res = client
				.target("https://service.com/users/345")
				.request(MediaType.APPLICATION_JSON).get().readEntity(String.class);
		
		String result = PASS;
		if(res == null) result = FAIL;
		
		System.out.println("Result: " + res);
		System.out.println("\n\n*** Test case '" + Thread.currentThread().getStackTrace()[1].getMethodName() +"' finished");
	}

    public void getUser()
    {
        System.out.println("\n\n*** Starting test case '" + Thread.currentThread().getStackTrace()[1].getMethodName() +"'...");

        User user = client
                .target("https://service.com/users/345")
                .request(MediaType.APPLICATION_JSON)
                .get(User.class);

        String result = PASS;
        if(user == null) result = FAIL;

        System.out.println("Result: " + user.getProfession());
        System.out.println("\n\n*** Test case '" + Thread.currentThread().getStackTrace()[1].getMethodName() +"' finished");
    }

}
