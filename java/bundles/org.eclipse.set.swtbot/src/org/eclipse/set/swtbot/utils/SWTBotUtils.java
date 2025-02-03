package org.eclipse.set.swtbot.utils;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.freeze.FreezeLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.set.utils.table.BodyLayerStack;
import org.eclipse.swtbot.nebula.nattable.finder.SWTNatTableBot;
import org.eclipse.swtbot.nebula.nattable.finder.widgets.SWTBotNatTable;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;

/**
 * Utilities for working with Nattable in SWTBot
 */
public class SWTBotUtils {
	/**
	 * Waits for any nattable to be visible
	 * 
	 * @param bot     The SWT Bot
	 * @param timeout Timeout for waiting
	 * @return The SWTBotNatTable instance for accessing the nattable
	 */
	public static SWTBotNatTable waitForNattable(SWTBot bot, int timeout) {
		var condition = new DefaultCondition() {
			SWTBotNatTable nattableBot = null;

			@Override
			public String getFailureMessage() {
				return "Failed to find any nattable";
			}

			@Override
			public boolean test() throws Exception {
				nattableBot = new SWTNatTableBot().nattable();
				return nattableBot != null;
			}
		};

		bot.waitUntil(condition, timeout);
		return condition.nattableBot;
	}

	/**
	 * @param gridLayer         The gridLayer
	 * @param bodyLayerStack    The BodyLayerStack
	 * @param columnHeaderLayer The columnHeaderLayer
	 */
	public record NattableLayers(GridLayer gridLayer, BodyLayerStack bodyLayerStack, ILayer columnHeaderLayer) {

		/**
		 * @return the selection layer
		 */
		public ILayer selectionLayer() {
			return bodyLayerStack.getSelectionLayer();
		}

		/**
		 * @return The freeze layer
		 */
		public FreezeLayer freezeLayer() {
			return bodyLayerStack.getFreezeLayer();
		}

	}

	/**
	 * @param nattableBot the nattable bot for the nattable
	 * @return a record of commonly used layers
	 */
	public static NattableLayers getNattableLayers(SWTBotNatTable nattableBot) {
		final NatTable natTable = nattableBot.widget;
		final ILayer layer = natTable.getLayer();
		assertInstanceOf(GridLayer.class, layer);
		final GridLayer gridLayer = (GridLayer) layer;
		assertInstanceOf(BodyLayerStack.class, gridLayer.getBodyLayer());
		final BodyLayerStack bodyLayerStack = (BodyLayerStack) gridLayer.getBodyLayer();

		return new NattableLayers(gridLayer, bodyLayerStack, gridLayer.getColumnHeaderLayer());
	}

}
