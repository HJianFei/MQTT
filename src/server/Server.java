package server;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import callback.PushCallback;

public class Server {
	public static final String HOST = "tcp://iot.eclipse.org:1883";
	public static final String TOPIC = "LLRP_1";
	private static final String clientid = "server";

	private MqttClient client;
	private MqttTopic topic;
	private String userName = "admin";
	private String passWord = "password";

	private MqttMessage message;

	public Server() throws MqttException {
		// MemoryPersistence����clientid�ı�����ʽ��Ĭ��Ϊ���ڴ汣��
		client = new MqttClient(HOST, clientid, new MemoryPersistence());
		connect();
	}

	private void connect() {
		MqttConnectOptions options = new MqttConnectOptions();
		options.setCleanSession(false);
		options.setUserName(userName);
		options.setPassword(passWord.toCharArray());
		// ���ó�ʱʱ��
		options.setConnectionTimeout(10);
		// ���ûỰ����ʱ��
		options.setKeepAliveInterval(20);
		try {
			client.setCallback(new PushCallback());
			client.connect(options);
			topic = client.getTopic(TOPIC);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void publish(MqttTopic topic, MqttMessage message)
			throws MqttPersistenceException, MqttException {
		MqttDeliveryToken token = topic.publish(message);
		token.waitForCompletion();
	}

	public static void main(String[] args) throws MqttException {
		Server server = new Server();
		for (int i = 0; i < 100; i++) {
		server.message = new MqttMessage();
		server.message.setQos(2);
		server.message.setRetained(true);
		server.message.setPayload(("���͵���Ϣ:"+i).getBytes());
		server.publish(server.topic, server.message);
		
		}
		System.out.println(server.message.isRetained() + "------ratained״̬");
	}
}
