package macro.buddy;

import macro.buddy.ui.builds.BuildUI;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class Launcher {
	private static final Logger LOGGER = LogManager.getLogger(Launcher.class);

	public static void main(String[] args) {
		checkLogLevels();
		LOGGER.info("Initializing Exile Buddy");
		new BuildUI().init(args);
	}

	private static void checkLogLevels() {
		Arrays.stream(Level.values()).sorted().forEach(level -> LOGGER.log(level, "{} is Visible", level.name()));
	}
}