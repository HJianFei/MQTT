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
		// 连接丢失后，一般在这里面进行重连
		System.out.println("连接断开，可以做重连");
		btn_start.setEnabled(true);
	}

	@Override
	public void messageArrived(String topic, MqttMessage message)
			throws Exception {
		// subscribe后得到的消息会执行到这里面
		// new WarnFrame().setVisible(true);
		URL musicUrl = new URL("file:"
				+ System.getProperty("user.dir").toString() + "\\abc.mp3"); // 音乐URL

		AudioClip ac = Applet.newAudioClip(musicUrl);
		ac.play();
		ac.loop();
		int i = JOptionPane.showConfirmDialog(null, "警告！有货物被盗了，立即查看?", "警告！！！",
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
