/*
 * @author Emil
 */
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class TCPServer {

    /**
     * @param args the command line arguments
     */
    int portN = 4032;
    int millisecondsTimeout = 50;
    List<Socket> sockets = new ArrayList<Socket>(); 
	List<Worker> workers = new ArrayList<Worker>();

    ServerSocket servSock;
    public static void main(String[] args) throws IOException
    {
        TCPServer serv = new TCPServer();
        serv.Host();
        /// Host server.
        while (true)
        {
            serv.AcceptClients();
			serv.CleanOldWorkers();
            if (serv.sockets.size() > 0)
                serv.ReadIncomingData();
        }
    }
    void Host() throws IOException
    {
        servSock = new ServerSocket(portN);
        servSock.setSoTimeout(millisecondsTimeout);
/*    try{} catch (java.io.InterruptedIOException)
        {
        }
*/
        System.out.println("Launching tcp listener server on port: "+portN);
    }
    static int count = 0;
    void AcceptClients() throws IOException
    {
        /// check old ones, if any disconnected
        for (int i = 0; i < sockets.size(); ++i)
        {
            Socket sock = sockets.get(i);
            if (sock.isClosed() || !sock.isConnected())
            {
                sockets.remove(sock);
                System.out.println("Client disconnected or socket closed");
                System.out.println("Num clients: "+sockets.size());
            }
        }
        while (true)
        {
            try {
                Socket socket = servSock.accept();
                sockets.add(socket);
            }
            catch (java.io.InterruptedIOException ioe)
            {
                break; // No new client, just break the loop.
            }
			catch (java.net.SocketException e)
			{
				System.out.println("Unable to accept incoming connection. Too many simultaneous connections: "+e.toString());
				break;
			}
            System.out.println("Incoming client");
            System.out.println("Num clients: "+sockets.size());
        }
//        count++;
  //      if (count > 50){
    //        System.out.println("Waiting for clients");
      //      count = 0;
  //      }
    }
	void CleanOldWorkers()
	{
		// Clear old finished workers.
		for (int i = 0; i < workers.size(); ++i)
		{
			Worker w = workers.get(i);
			if (w.workIsDone){
				workers.remove(w);
				System.out.println("Worker done");
			}
		}
	}
	char[] receiveBuf = new char[2000];
    void ReadIncomingData() throws IOException 
    {
    //    System.out.print("Incoming data");
        boolean incData = false;
        for (int i = 0; i < sockets.size(); ++i)
        {
            Socket sock = sockets.get(i);
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
      //      System.out.println("Making new buffered readerrr");
            if (!in.ready())
                continue;
			int bytesRead = in.read(receiveBuf, 0, 2000); // Read incoming 2k chars as far as possible/needed.
            System.out.println("recv "+bytesRead+" bytes");
            /// Reply?
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
			if (workers.size() < 20)
			{
				Worker w = new Worker();
				w.DoWork();
				workers.add(w);
				out.println("Starting heavy calculation: Calc jobs: "+workers.size());
				System.out.println("Starting worker.");
			}
			else
			{
				out.println("Reached worker limit. Rejecting request.");
			}
            out.flush();
        }
    }
}
