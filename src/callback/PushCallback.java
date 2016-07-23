package callback;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.net.URI;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class PushCallback implements MqttCallback {

	private javax.swing.JButton btn_start;

	public PushCallback(JButton btn_start) {

		this.btn_start = btn_start;
	}

	public PushCallback() {

	}

	@Override
	public void connectionLost(Throwable cause) {
		// ���Ӷ�ʧ��һ�����������������
		System.out.println("���ӶϿ�������������");
		btn_start.setEnabled(true);
	}

	@Override
	public void messageArrived(String topic, MqttMessage message)
			throws Exception {
		// subscribe��õ�����Ϣ��ִ�е�������
		// new WarnFrame().setVisible(true);
		URL musicUrl = new URL("file:"
				+ System.getProperty("user.dir").toString() + "\\abc.mp3"); // ����URL

		AudioClip ac = Applet.newAudioClip(musicUrl);
		ac.play();
		ac.loop();
		int i = JOptionPane.showConfirmDialog(null, "���棡�л��ﱻ���ˣ������鿴?", "���棡����",
				JOptionPane.YES_NO_OPTION);
		Toolkit.getDefaultToolkit().beep();
		if (i == 0) {
			ac.stop();
			Desktop desktop = Desktop.getDesktop();
			desktop.browse(new URI("http://www.baidu.com"));
		} else {
			ac.stop();
		}
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {

		// System.out.println("deliveryComplete---------" + token.isComplete());
	}

}
