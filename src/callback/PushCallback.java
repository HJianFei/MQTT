package callback;

import java.applet.AudioClip;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class PushCallback implements MqttCallback {

	private javax.swing.JButton btn_start;
	private javax.swing.JButton btn_look;
	private javax.swing.JLabel label_text;
	private AudioClip ac;

	public PushCallback(JButton btn_start, JButton btn_look, JLabel label_text,
			AudioClip ac) {
		super();
		this.btn_start = btn_start;
		this.btn_look = btn_look;
		this.btn_start = btn_start;
		this.label_text = label_text;
		this.ac = ac;

	}

	public PushCallback() {

	}

	@Override
	public void connectionLost(Throwable cause) {
		// ���Ӷ�ʧ��һ�����������������
		// System.out.println("���ӶϿ�������������");
		label_text.setText("�������Ͽ�������������");
		btn_start.setEnabled(true);
	}

	@Override
	public void messageArrived(String topic, MqttMessage message)
			throws Exception {
		// subscribe��õ�����Ϣ��ִ�е�������
		// new WarnFrame().setVisible(true);
		ac.play();
		ac.loop();
		btn_look.setEnabled(true);
		
		btn_look.setBackground(Color.RED);
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {

		// System.out.println("deliveryComplete---------" + token.isComplete());
	}

}
