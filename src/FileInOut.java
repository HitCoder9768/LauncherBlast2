import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

public class FileInOut {
	
	public String getLaunchCommand(boolean setSkin, String skinName, boolean setColor, int colorID, boolean openGL,
			boolean windowed, boolean manualViewport, int[] windowPort, boolean disableSFX, int musicModeIndex,
			boolean separateConsoleWindow, boolean debug, boolean ultimate, boolean warpToMap,
			int warpMapNumber, int demoTypeIndex, String demoFile, String clientName, String cliArgs,
			String[] addonFiles, boolean runScript, String scriptName,
			boolean isMultiplayerGame, boolean setName, String userName, boolean isHosting,
			String connectTo, boolean noDownload, boolean localHost,
			String serverName, boolean isDedicated, boolean useMasterServ, String roomID, boolean useAdminPassword, String adminPassword, boolean upload,
			int gameTypeIndex, boolean enablePointlimit, boolean enableTimeLimit, int pointLimit, int timeLimit,
			boolean playerCap, int maxPlayers, boolean noWeaponRings, boolean suddenDeath, int advanceMapIndex, boolean forceSkin,
			int forceSkinIndex,
			boolean useLauncherPath, String workingDirectory, boolean useWine,
			String operatingSystem) {
		
		String command = "";
		if(!useLauncherPath) {
			command+=workingDirectory;
			if(!workingDirectory.endsWith(File.separator))
				command+=File.separator;
		}
		if(operatingSystem.indexOf("win")>=0) {
			// Windows
			command+=clientName+" ";
		}else if((operatingSystem.indexOf("nix") >= 0 || operatingSystem.indexOf("nux") >= 0 || operatingSystem.indexOf("aix") > 0 ) || (operatingSystem.indexOf("mac") >= 0)) {
			// Mac or Linux (or unix)
			
			if (useWine) {
				// Run the game in WINE
				command+="wine "+clientName+" ";
			}else if(operatingSystem.indexOf("mac") >= 0){
				JOptionPane.showMessageDialog(null, "Sorry! Currently this launcher does not work on Mac/OSX! :(", "Platform not supported", JOptionPane.WARNING_MESSAGE);
				return "";
			}else {
				if(command=="")
					command+="./";
				command+=clientName+" ";
			}
			
		}else {
			JOptionPane.showMessageDialog(null, "Sorry! Currently this launcher does not support your operating system! ( "+operatingSystem+" )", "Platform not supported", JOptionPane.WARNING_MESSAGE);
			return "";
		}
		
		if(setSkin)
			command+="+skin "+skinName+" ";
		
		if(setColor)
			command+="+color "+colorID+" ";
		
		if(openGL)
			command+="-opengl ";
		
		if(windowed)
			command+="-win ";
		
		if(manualViewport)
			command+="-width "+windowPort[0]+" -height"+windowPort[1]+" ";
		
		if(disableSFX)
			command+="-nosound ";
		
		switch(musicModeIndex) {
			case 1:
				//midi
				command+="-nodigmusic ";
				break;
			case 2:
				//cd
				command+="-usecd ";
				break;
			case 3:
				//none
				command+="-nomusic ";
				break;
		}
		
		if(separateConsoleWindow)
			command+="-console ";
		
		if(debug)
			command+="-debug ";
		
		if(ultimate)
			command+="-ultimatemode ";
		
		if(warpToMap)
			command+="-warp "+warpMapNumber+" ";
		
		switch(demoTypeIndex) {
		case 1:
			//record
			command+="-record "+demoFile+" ";
			break;
		case 2:
			//play
			command+="-playdemo "+demoFile+" ";
			break;
		case 3:
			//time
			command+="-timedemo "+demoFile+" ";
			break;
		}
		
		if(runScript)
			command+="+exec "+scriptName+" ";
		
		if(isMultiplayerGame) {
			if(setName)
				command+="-name "+userName+" ";
			
			if(isHosting){
				// Host
				if(isDedicated)
					command+="-dedicated ";
				else
					command+="-server ";
				
				if(!upload)
					command+="-noupload ";
				
				if(useMasterServ)
					command+="-room "+roomID+" ";
				if(useAdminPassword)
					command+="-password "+adminPassword+" ";
				command+="-gametype "+gameTypeIndex+" ";
				
				command+="+advancemap "+advanceMapIndex+" ";
				
				if(!serverName.isEmpty() && serverName!="" && !serverName.contains("\""))
					command+="+servername \""+serverName+"\"";
				
				if(enablePointlimit)
					command+="+pointlimit "+pointLimit+" ";
				
				if(enableTimeLimit)
					command+="+timelimit "+timeLimit+" ";
				
				if(playerCap)
					command+="+maxplayers "+maxPlayers+" ";
				
				if(noWeaponRings)
					command+="+specialrings 0 ";
				else
					command+="+specialrings 1 ";
				
				if(suddenDeath)
					command+="+suddendeath 1 ";
				else
					command+="+suddendeath 0 ";
				
				if(forceSkin)
					command+="+forceskin "+forceSkinIndex+" ";
				
			}else{
				// Join
				if(localHost) {
					command+="-clientport 5030 -connect 127.0.0.1 ";
				}else
					command+="-connect "+connectTo+" ";
				if(noDownload)
					command+="-nodownload ";
			}
		}
		
		command+=cliArgs+" ";
		
		if(!(addonFiles.length<1)) {
			command+="-file ";
			for(int i=0;i<addonFiles.length;i++) {
				command+=addonFiles[i]+" ";
			}
		}
		
		System.out.println("command generated!");
		System.out.println("");
		System.out.println(command);
		System.out.println("");
		System.out.println("");
		return command;
		
	}
	
	
	public void saveParameters(File fileToWrite, boolean setSkin, String skinName, boolean setColor, int colorID, boolean openGL,
			boolean windowed, boolean manualViewport, int[] windowPort, boolean disableSFX, int musicModeIndex,
			boolean separateConsoleWindow, boolean debug, boolean ultimate, boolean warpToMap,
			int warpMapNumber, int demoTypeIndex, String demoFile, String clientName, String cliArgs,
			String[] addonFiles, boolean runScript, String scriptName,
			boolean isMultiplayerGame, boolean setName, String userName, boolean isHosting,
			String connectTo, boolean noDownload, boolean localHost,
			String serverName, boolean isDedicated, boolean useMasterServ, String roomID, boolean useAdminPassword, String adminPassword, boolean upload,
			int gameTypeIndex, boolean enablePointlimit, boolean enableTimeLimit, int pointLimit, int timeLimit,
			boolean playerCap, int maxPlayers, boolean noWeaponRings, boolean suddenDeath, int advanceMapIndex, boolean forceSkin,
			int forceSkinIndex,
			boolean useLauncherPath, String workingDirectory, boolean useWine)
	{
		
		try {
			FileWriter write = new FileWriter(fileToWrite);
			
			write.write("[GAME]");
			write.write(System.getProperty("line.separator"));
			write.write("SetSkin="+setSkin);
			write.write(System.getProperty("line.separator"));
			write.write("Skin="+skinName);
			write.write(System.getProperty("line.separator"));
			write.write("SetColor="+setColor);
			write.write(System.getProperty("line.separator"));
			write.write("Color="+colorID);
			write.write(System.getProperty("line.separator"));
			write.write("WarpToMap="+warpToMap);
			write.write(System.getProperty("line.separator"));
			write.write("WarpMapNumber="+warpMapNumber);
			write.write(System.getProperty("line.separator"));
			
			write.write("[ENGINE]");
			write.write(System.getProperty("line.separator"));
			write.write("Executable="+clientName);
			write.write(System.getProperty("line.separator"));
			write.write("UseOpenGL="+openGL);
			write.write(System.getProperty("line.separator"));
			write.write("WindowedMode="+windowed);
			write.write(System.getProperty("line.separator"));
			write.write("ManualViewport="+manualViewport);
			write.write(System.getProperty("line.separator"));
			if(windowPort.length==2)
				write.write("WindowPort="+windowPort[0]+"x"+windowPort[1]);
			write.write(System.getProperty("line.separator"));
			write.write("DisableSoundEffects="+disableSFX);
			write.write(System.getProperty("line.separator"));
			write.write("MusicEngine="+musicModeIndex);
			write.write(System.getProperty("line.separator"));
			
			write.write("[MISC]");
			write.write(System.getProperty("line.separator"));
			write.write("SeparateConsoleWindow="+separateConsoleWindow);
			write.write(System.getProperty("line.separator"));
			write.write("Debug="+debug);
			write.write(System.getProperty("line.separator"));
			write.write("Ultimate="+ultimate);
			write.write(System.getProperty("line.separator"));
			write.write("RecordDemo="+demoTypeIndex);
			write.write(System.getProperty("line.separator"));
			write.write("DemoFile="+demoFile);
			write.write(System.getProperty("line.separator"));
			write.write("RunScript="+runScript);
			write.write(System.getProperty("line.separator"));
			write.write("ScriptName="+scriptName);
			write.write(System.getProperty("line.separator"));
			write.write("CustomParameters="+cliArgs);
			write.write(System.getProperty("line.separator"));
			
			write.write("[FILES]");
			write.write(System.getProperty("line.separator"));
			for(int i=0;i<addonFiles.length;i++) {
				write.write("File="+addonFiles[i]);
				write.write(System.getProperty("line.separator"));
			}

			write.write("[NETWORK]");
			write.write(System.getProperty("line.separator"));
			write.write("IsOnline="+isMultiplayerGame);
			write.write(System.getProperty("line.separator"));
			write.write("SetName="+setName);
			write.write(System.getProperty("line.separator"));
			write.write("UserName="+userName);
			write.write(System.getProperty("line.separator"));
			write.write("IsHost="+isHosting);
			write.write(System.getProperty("line.separator"));
			
			write.write("[CLIENT]");
			write.write(System.getProperty("line.separator"));
			write.write("Host="+connectTo);
			write.write(System.getProperty("line.separator"));
			write.write("DoNotDownload="+noDownload);
			write.write(System.getProperty("line.separator"));
			write.write("JoinOwnServ="+localHost);
			write.write(System.getProperty("line.separator"));
			
			write.write("[HOST]");
			write.write(System.getProperty("line.separator"));
			write.write("Name="+serverName);
			write.write(System.getProperty("line.separator"));
			write.write("Dedicated="+isDedicated);
			write.write(System.getProperty("line.separator"));
			write.write("UseMS="+useMasterServ);
			write.write(System.getProperty("line.separator"));
			write.write("AllowDownloading="+upload);
			write.write(System.getProperty("line.separator"));
			write.write("RoomID="+roomID);
			write.write(System.getProperty("line.separator"));
			write.write("UseAdminPass="+useAdminPassword);
			write.write(System.getProperty("line.separator"));
			write.write("AdminPassword="+adminPassword);
			write.write(System.getProperty("line.separator"));
			write.write("GameType="+gameTypeIndex);
			write.write(System.getProperty("line.separator"));
			write.write("EnablePointLimit="+enablePointlimit);
			write.write(System.getProperty("line.separator"));
			write.write("PointLimit="+pointLimit);
			write.write(System.getProperty("line.separator"));
			write.write("EnableTimeLimit="+enableTimeLimit);
			write.write(System.getProperty("line.separator"));
			write.write("TimeLimit="+timeLimit);
			write.write(System.getProperty("line.separator"));
			write.write("CapPlayers="+playerCap);
			write.write(System.getProperty("line.separator"));
			write.write("MaxPlayers="+maxPlayers);
			write.write(System.getProperty("line.separator"));
			write.write("NoWeaponRings="+noWeaponRings);
			write.write(System.getProperty("line.separator"));
			write.write("SuddenDeath="+suddenDeath);
			write.write(System.getProperty("line.separator"));
			write.write("AdvanceToNextLevel="+advanceMapIndex);
			write.write(System.getProperty("line.separator"));
			write.write("ForceSkin="+forceSkin);
			write.write(System.getProperty("line.separator"));
			write.write("SkinToForce="+forceSkinIndex);
			write.write(System.getProperty("line.separator"));
			
			write.write("[LAUNCHER]");
			write.write(System.getProperty("line.separator"));
			write.write("UseDefaultPath="+useLauncherPath);
			write.write(System.getProperty("line.separator"));
			write.write("WorkingDirectory="+workingDirectory);
			write.write(System.getProperty("line.separator"));
			write.write("UseWINE="+useWine);
			
			write.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
