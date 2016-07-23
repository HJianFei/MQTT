package client;

import java.applet.AudioClip;

import javax.swing.JButton;
import javax.swing.JLabel;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import callback.PushCallback;

public class Listener {

	private String host = "";
	private String topics = "";
	private String client_name = "";
	private MqttClient client;
	private MqttConnectOptions options;
	private javax.swing.JButton btn_start;
	private javax.swing.JButton btn_look;
	private javax.swing.JLabel label_text;
	private AudioClip ac;

	public Listener(String hOST, String tOPIC, String cLIENT_NAME,
			JButton btn_start, JButton btn_look, JLabel label_text, AudioClip ac) {
		super();
		this.host = hOST;
		this.topics = tOPIC;
		this.client_name = cLIENT_NAME;
		this.btn_start = btn_start;
		this.btn_look = btn_look;
		this.label_text = label_text;
		this.ac = ac;
	}

	public String startListener() {

		try {
			// host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
			client = new MqttClient(host, client_name, new MemoryPersistence());
			// MQTT的连接设置
			options = new MqttConnectOptions();
			// 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
			options.setCleanSession(true);
			// 设置连接的用户名
			// options.setUserName(userName);
			// // 设置连接的密码
			// options.setPassword(passWord.toCharArray());
			// 设置超时时间 单位为秒
			options.setConnectionTimeout(10);
			// 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
			options.setKeepAliveInterval(20);
			// 设置回调
			client.setCallback(new PushCallback(btn_start, btn_look,
					label_text, ac));
			MqttTopic topic = client.getTopic(topics);
			// setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
			options.setWill(topic, "close".getBytes(), 2, true);

			client.connect(options);
			// 订阅消息
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
