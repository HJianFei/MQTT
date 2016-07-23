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
		// 连接丢失后，一般在这里面进行重连
		// System.out.println("连接断开，可以做重连");
		label_text.setText("服务器断开，请重新链接");
		btn_start.setEnabled(true);
	}

	@Override
	public void messageArrived(String topic, MqttMessage message)
			throws Exception {
		// subscribe后得到的消息会执行到这里面
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
