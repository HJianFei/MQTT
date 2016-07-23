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
			// hostΪ��������clientid������MQTT�Ŀͻ���ID��һ����Ψһ��ʶ����ʾ��MemoryPersistence����clientid�ı�����ʽ��Ĭ��Ϊ���ڴ汣��
			client = new MqttClient(host, client_name, new MemoryPersistence());
			// MQTT����������
			options = new MqttConnectOptions();
			// �����Ƿ����session,�����������Ϊfalse��ʾ�������ᱣ���ͻ��˵����Ӽ�¼����������Ϊtrue��ʾÿ�����ӵ������������µ��������
			options.setCleanSession(true);
			// �������ӵ��û���
			// options.setUserName(userName);
			// // �������ӵ�����
			// options.setPassword(passWord.toCharArray());
			// ���ó�ʱʱ�� ��λΪ��
			options.setConnectionTimeout(10);
			// ���ûỰ����ʱ�� ��λΪ�� ��������ÿ��1.5*20���ʱ����ͻ��˷��͸���Ϣ�жϿͻ����Ƿ����ߣ������������û�������Ļ���
			options.setKeepAliveInterval(20);
			// ���ûص�
			client.setCallback(new PushCallback(btn_start, btn_look,
					label_text, ac));
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
