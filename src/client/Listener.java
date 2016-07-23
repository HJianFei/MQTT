package client;

import javax.swing.JButton;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import callback.PushCallback;

public class Listener {
	
	private String host="";
	private String topics = "";
	private String client_name = "";
	private String userName ="";
	private String passWord = "";
	private MqttClient client;
	private MqttConnectOptions options;
	private javax.swing.JButton btn_start;
	
	
	public Listener(String hOST, String tOPIC, String cLIENT_NAME,
			String userName, String passWord,JButton btn_start) {
		super();
		host = hOST;
		topics = tOPIC;
		client_name = cLIENT_NAME;
		this.userName = userName;
		this.passWord = passWord;
		this.btn_start=btn_start;
	}


	public String startListener(){
		
		try {
			// hostΪ��������clientid������MQTT�Ŀͻ���ID��һ����Ψһ��ʶ����ʾ��MemoryPersistence����clientid�ı�����ʽ��Ĭ��Ϊ���ڴ汣��
			client = new MqttClient(host, client_name, new MemoryPersistence());
			// MQTT����������
			options = new MqttConnectOptions();
			// �����Ƿ����session,�����������Ϊfalse��ʾ�������ᱣ���ͻ��˵����Ӽ�¼����������Ϊtrue��ʾÿ�����ӵ������������µ��������
			options.setCleanSession(true);
			// �������ӵ��û���
			options.setUserName(userName);
			// �������ӵ�����
			options.setPassword(passWord.toCharArray());
			// ���ó�ʱʱ�� ��λΪ��
			options.setConnectionTimeout(10);
			// ���ûỰ����ʱ�� ��λΪ�� ��������ÿ��1.5*20���ʱ����ͻ��˷��͸���Ϣ�жϿͻ����Ƿ����ߣ������������û�������Ļ���
			options.setKeepAliveInterval(20);
			// ���ûص�
			client.setCallback(new PushCallback(btn_start));
			MqttTopic topic = client.getTopic(topics);
			// setWill�����������Ŀ����Ҫ֪���ͻ����Ƿ���߿��Ե��ø÷������������ն˿ڵ�֪ͨ��Ϣ
			options.setWill(topic, "close".getBytes(), 2, true);

			client.connect(options);
			// ������Ϣ
			int[] Qos = { 2 };
			String[] topic1 = { topics };
			client.subscribe(topic1, Qos);
			return "OK";

		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR";
		}
	}

}
