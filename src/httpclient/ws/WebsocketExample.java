package httpclient.ws;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class WebsocketExample {

    public static void main(String... args) throws Exception {

        int msgCount = 5;
        
        CompletableFuture<WebSocket> wsFuture = HttpClient.newHttpClient()
        		.newWebSocketBuilder()
        		.connectTimeout(Duration.ofSeconds(3))
        		.buildAsync(URI.create("wss://demo.piesocket.com/v3/channel_1?api_key=oCdCMcMPQpbvNjUIzqtvF1d2X2okWpDQj4AwARJuAgtjhzKxVEjQU6IdCjwm&notify_self"), new EchoListener());
        		//.buildAsync(URI.create("wss://echo.websocket.org"), new EchoListener());
        
               
        wsFuture.thenAccept(webSocket -> {        
        	webSocket.request(msgCount + 1);
        	for (int i = 0; i < msgCount; ++i) {
				webSocket.sendText("Message " + i, true);
			}
        	
        	
        }).exceptionally(e -> { 
        	System.out.println(e.getMessage());
        	return null;
        });
        

    	try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
        
    }


    static class EchoListener implements WebSocket.Listener {

        @Override
        public void onOpen(WebSocket webSocket) {
            System.out.println("WebSocket onOpen");
        }

        @Override
        public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        	System.out.println("onText " + data);
            return null;
            
        }


    }
}
