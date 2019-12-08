package org.swampscottcurrents.serpentui;

import java.util.List;

import com.google.common.collect.ImmutableList;

import edu.wpi.first.shuffleboard.api.plugin.Description;
import edu.wpi.first.shuffleboard.api.plugin.Plugin;
import edu.wpi.first.shuffleboard.api.widget.ComponentType;
import edu.wpi.first.shuffleboard.api.widget.WidgetType;

@Description(group = "SerpentFramework", name = "SerpentUI", summary = "Team 4311 robot control plugin, designed to allow selection of movement targets on the field.", version = "0.0.2")
public class App extends Plugin {

	public App() {
		super();
		NetworkBinding.Initialize();
	}

	@Override
	@SuppressWarnings("all")
	public List<ComponentType> getComponents() {
		return ImmutableList.of(WidgetType.forAnnotatedWidget(PathControlWidget.class));
	}
}