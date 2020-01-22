package github.macro.ui.selector;

import github.macro.build_info.BuildInfo;

/**
 * Created by Macro303 on 2020-Jan-22.
 */
public class SelectorController {
	private final SelectorModel model;

	public SelectorController(SelectorModel model){
		this.model = model;
	}

	public void updateBuild(BuildInfo build){
		model.setSelectedBuild(build);
	}
}