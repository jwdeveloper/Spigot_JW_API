package jw.web_socket;

import jw.InitializerAPI;
import jw.dependency_injection.Injectable;
import jw.task.TaskTimer;
import jw.utilites.binding.BindingField;
import jw.web_socket.packet.PacketProperty;
import org.bukkit.Bukkit;
import org.java_websocket.WebSocket;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.function.Consumer;

@Injectable(autoInit = true)
public abstract class WebSocketPacket implements PacketInvokeEvent
{
    private int packetSize =0;
    private int packetIdSize = 4;
    private List<BindingField<Object>> fieldList = new ArrayList<>();
    private Queue<Consumer<WebSocket>> tasks  = new LinkedList<>();
    private TaskTimer taskTimer;

    public abstract void onPacketTriggered(WebSocket webSocket);

    public abstract int getPacketId();

    public WebSocketPacket()
    {
        loadPacketFields();
        packetSize = getPacketSize();
        taskTimer = new TaskTimer(1,(time, taskTimer1) ->
        {
            for(Consumer<WebSocket> webSocket:tasks)
            {
                webSocket.accept(null);
            }
            tasks.clear();
        });
        taskTimer.run();
    }
    protected void addSpigotTask(Consumer<WebSocket> consumer)
    {
        tasks.add(consumer);
    }

    private void loadPacketFields()
    {
        for(Field field:this.getClass().getDeclaredFields())
        {
            if (field.getAnnotation(PacketProperty.class) == null)
                continue;
            fieldList.add(new BindingField<>(field,this));
        }
    }

    public boolean resolvePacket(ByteBuffer buffer)
    {
        if(packetSize != buffer.limit())
        {
            return false;
        }
        int bufferIndex =packetIdSize;
        int start = packetIdSize;
        Object value  = null;
        Type type;
        for(int i=0;i<fieldList.size();i++)
        {
            try
            {
                start = bufferIndex;
                type = fieldList.get(i).getType();

                if (type.getTypeName().equals("int"))
                {
                    bufferIndex = start+4;
                    value = buffer.getInt(start);
                }
                if (type.getTypeName().equals("byte"))
                {
                    bufferIndex = start+1;
                    value = buffer.get(start);
                }
                if (type.getTypeName().equals("bool"))
                {
                    bufferIndex = start+1;
                    value = buffer.get(start) != 0;
                }
                fieldList.get(i).set(value);
            }
            catch (Exception e)
            {
                InitializerAPI.errorLog("Packet resolver error "+this.getClass().getSimpleName()+e.getMessage());
            }
        }
        return true;
    }

   private int getPacketSize()
   {
       int size =0;
       for(BindingField<Object> bindingField:fieldList)
       {
           if (bindingField.getType().getTypeName().equals("byte") ||
               bindingField.getType().getTypeName().equals("bool")
           )
           {
               size+=1;
           }
           if (bindingField.getType().getTypeName().equals("int"))
           {
               size+=4;
           }
       }
       return packetIdSize + size;
   }


}
