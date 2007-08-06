package profilechecker.parser;

import java.io.File;

import javax.swing.JEditorPane;
import javax.swing.JFrame;

import junit.framework.TestCase;
import profilechecker.parser.XMIParserGUI.ActionListenerImplementation;

public class XMIParserGUITest extends TestCase {
	
	public void testAction() {
		JEditorPane jep = new JEditorPane();
		JFrame jframe = new JFrame();
		ActionListenerImplementation acl = new ActionListenerImplementation(jep, jframe);
		acl.parseFile(new File("resources-test/profile_multi_stereotype_definition.xmi"));
		String expectedResult = "<html><body>Profile<ul><li><b>name</b> public<li><b>id</b> _12_5_1_12e803d1_1186331641532_79382_142<li><b>visibility</b> publicV<p><li>Stereotype<ul><li><b>name</b> SimpleStereotype<li><b>id</b> _12_5_1_12e803d1_1186332374248_452459_298<li><b>visibility</b> publicV<li><b>type</b> UML Standard Profile::UML2.0 Metamodel::Classes::Kernel::Class<li><b>type</b> UML Standard Profile::UML2.0 Metamodel::Classes::Kernel::Association</ul></ul><hr /></body></html>";
		assertEquals(expectedResult, jep.getText());
	}
	
	public void testCreation() {
		XMIParserGUI xmiParserGUI = new XMIParserGUI();
	}
	
}
