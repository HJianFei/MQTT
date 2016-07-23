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
		// MemoryPersistence设置clientid的保存形式，默认为以内存保存
		client = new MqttClient(HOST, clientid, new MemoryPersistence());
		connect();
	}

	private void connect() {
		MqttConnectOptions options = new MqttConnectOptions();
		options.setCleanSession(false);
		options.setUserName(userName);
		options.setPassword(passWord.toCharArray());
		// 设置超时时间
		options.setConnectionTimeout(10);
		// 设置会话心跳时间
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
		server.message.setPayload(("推送的信息:"+i).getBytes());
		server.publish(server.topic, server.message);
		
		}
		System.out.println(server.message.isRetained() + "------ratained状态");
	}
}
