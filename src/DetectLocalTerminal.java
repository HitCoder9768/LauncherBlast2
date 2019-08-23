import java.io.IOException;

public class DetectLocalTerminal {
	public String detectTerminal() {
		String terminalEmulator = "xterm";
		String[] terminals = {"xfce4-terminal","konsole","gnome-terminal","lxterm","lxterminal","wterm","rxvt-unicode","mrxvt","Terminator","Terminology","rxvt","tilda","aterm","guake","kuake","yakuake"};
		
		for(int i = 0; i<terminals.length;i++) {
			if(isInstalled(terminals[i])) {
				terminalEmulator=terminals[i];
				System.out.println("Found terminal: "+terminals[i]);
				break;
			}
		}
		
		return terminalEmulator;
	}
	
	public boolean isInstalled(String program) {
		try {
			Process p = new ProcessBuilder(program).start();
			System.out.println(program+" seems to be installed!");
			p.destroy();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("something failed, "+program+" may not be installed.");
			return false;
		}
	}
}
