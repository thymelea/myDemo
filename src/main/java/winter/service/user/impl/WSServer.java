package winter.service.user.impl;

        import java.io.IOException;

        import javax.websocket.CloseReason;
        import javax.websocket.OnClose;
        import javax.websocket.OnError;
        import javax.websocket.OnMessage;
        import javax.websocket.OnOpen;
        import javax.websocket.Session;
        import javax.websocket.server.PathParam;
        import javax.websocket.server.ServerEndpoint;

//@ServerEndpoint注解中的内容是用户客户端websocket的连接url,例如ws://127.0.0.1:80/websocket/ws,
//结构形式为“ws：//ip:端口/项目名/指定的url”
@ServerEndpoint("/ws")
public class WSServer {
    private Session session;
    //连接打开时执行
    @OnOpen
    public void onOpen(Session session) {
        this.session=session;
        System.out.println("一个客户端连接进来了 ... 它的sessionid是：" + session.getId());
    }

    //收到消息时执行
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println(session.getId()+"客户端发送的消息是："+message);
        try{
            this.sendMessage(message);//消息发回给客户端
        }catch(Exception e){
            e.printStackTrace();
        }
        //return currentUser + "：" + message;如果有返回值，则客户端发送消息后会收到这个返回值
    }

    //连接关闭时执行
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("一个客户端关闭了，它的sessionid是：" + session.getId());
    }

    //连接错误时执行
    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }
    //自定义的方法，用于发送消息
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }
}
