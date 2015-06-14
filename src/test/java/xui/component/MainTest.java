package xui.component;

import org.pushingpixels.substance.api.skin.SubstanceTwilightLookAndFeel;
import tinyioc.context.AbstractApplicationContext;
import tinyioc.context.ClassPathXmlApplicationContext;
import xui.manager.XUIManagerUtils;

import javax.swing.*;

/**
 * Created by yongjie on 15-4-3.
 */
public class MainTest {

	private static void print(Object... o) {
		for (Object o1 : o) {
			System.out.println(o1.toString());
		}
	}

	public static void test() throws Exception {
		JFrame jFrame = new JFrame();
		jFrame.setLocation(200,200);
		jFrame.setSize(600, 400);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setVisible(true);
	}


	public static void test1() throws Exception {
		AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext("frame.xml");
		JFrame jframe2 = (JFrame) applicationContext.getBean("jframe2");
		XUIManagerUtils.BuildLayoutByManager(applicationContext);
		XUIManagerUtils.InitEventListener(applicationContext);
		XUIManagerUtils.DoSomething(applicationContext);
		jframe2.setVisible(true);
	}

	public static void test2() throws Exception {
		AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext("frame2.xml");
		JFrame jframe2 = (JFrame) applicationContext.getBean("jframe2");
		jframe2.setVisible(true);
	}

	public static void main(String[] args) throws Exception {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					long start= System.currentTimeMillis();
//					UIManager.setLookAndFeel(new SubstanceAutumnLookAndFeel());
//					UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());
//					UIManager.setLookAndFeel(new SubstanceBusinessBlueSteelLookAndFeel());
//					UIManager.setLookAndFeel(new SubstanceBusinessLookAndFeel());
//					UIManager.setLookAndFeel(new SubstanceChallengerDeepLookAndFeel());
//					UIManager.setLookAndFeel(new SubstanceCremeLookAndFeel());
//					UIManager.setLookAndFeel(new SubstanceCremeCoffeeLookAndFeel());
//					UIManager.setLookAndFeel(new SubstanceDustLookAndFeel());
//					UIManager.setLookAndFeel(new SubstanceDustCoffeeLookAndFeel());
//					UIManager.setLookAndFeel(new SubstanceEmeraldDuskLookAndFeel());
//					UIManager.setLookAndFeel(new SubstanceMagellanLookAndFeel());
//					UIManager.setLookAndFeel(new SubstanceMistAquaLookAndFeel());
//					UIManager.setLookAndFeel(new SubstanceMistSilverLookAndFeel());
//					UIManager.setLookAndFeel(new SubstanceModerateLookAndFeel());
//					UIManager.setLookAndFeel(new SubstanceNebulaLookAndFeel());
//					UIManager.setLookAndFeel(new SubstanceNebulaBrickWallLookAndFeel());
//					UIManager.setLookAndFeel(new SubstanceOfficeBlue2007LookAndFeel());
//					UIManager.setLookAndFeel(new SubstanceOfficeSilver2007LookAndFeel());
//					UIManager.setLookAndFeel(new SubstanceRavenLookAndFeel());
//					UIManager.setLookAndFeel(new SubstanceSaharaLookAndFeel());
					UIManager.setLookAndFeel(new SubstanceTwilightLookAndFeel());
					test1();
					long end = System.currentTimeMillis();
					print(end - start);
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		});
	}
}
