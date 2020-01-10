package macro.buddy;

import macro.buddy.builds.*;
import macro.buddy.ui.builds.BuildUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class Launcher {
	private static final Logger LOGGER = LogManager.getLogger(Launcher.class);

	public static void main(String[] args) {
		BuildUtils.loadBuilds();
		if(BuildUtils.getBuilds().size() == 0) {
			LOGGER.warn("No Builds found, creating a template build");
			new BuildInfo("Template", ClassTag.SCION, AscendencyTag.ASCENDANT, new ArrayList<>(), new ArrayList<>()).save();
		}
		new BuildUI().init(args);
	}
}