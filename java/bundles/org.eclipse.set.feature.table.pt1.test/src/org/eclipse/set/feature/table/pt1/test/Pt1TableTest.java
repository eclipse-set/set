/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.table.pt1.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.set.application.cacheservice.CacheServiceImpl;
import org.eclipse.set.application.geometry.GeoKanteGeometryServiceImpl;
import org.eclipse.set.application.geometry.GeoKanteGeometrySessionData;
import org.eclipse.set.application.graph.BankServiceImpl;
import org.eclipse.set.application.graph.BankingInformationSession;
import org.eclipse.set.application.graph.TopologicalGraphServiceImpl;
import org.eclipse.set.basis.Translateable;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.core.services.enumtranslation.EnumTranslation;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.geometry.GeoKanteGeometryService;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.feature.table.pt1.ssbb.SsbbTransformationService;
import org.eclipse.set.feature.table.pt1.ssit.SsitTransformationService;
import org.eclipse.set.feature.table.pt1.sska.SskaTransformationService;
import org.eclipse.set.feature.table.pt1.sskf.SskfTransformationService;
import org.eclipse.set.feature.table.pt1.sskg.SskgTransformationService;
import org.eclipse.set.feature.table.pt1.ssko.SskoTransformationService;
import org.eclipse.set.feature.table.pt1.sskp.SskpTransformationService;
import org.eclipse.set.feature.table.pt1.sskp.dm.SskpDmTransformationService;
import org.eclipse.set.feature.table.pt1.ssks.SsksTransformationService;
import org.eclipse.set.feature.table.pt1.sskt.SsktTransformationService;
import org.eclipse.set.feature.table.pt1.sskw.SskwTransformationService;
import org.eclipse.set.feature.table.pt1.sskx.SskxTransformationService;
import org.eclipse.set.feature.table.pt1.sskz.SskzTransformationService;
import org.eclipse.set.feature.table.pt1.ssla.SslaTransformationService;
import org.eclipse.set.feature.table.pt1.sslb.SslbTransformationService;
import org.eclipse.set.feature.table.pt1.ssld.SsldTransformationService;
import org.eclipse.set.feature.table.pt1.sslf.SslfTransformationService;
import org.eclipse.set.feature.table.pt1.ssli.SsliTransformationService;
import org.eclipse.set.feature.table.pt1.ssln.SslnTransformationService;
import org.eclipse.set.feature.table.pt1.sslr.SslrTransformationService;
import org.eclipse.set.feature.table.pt1.ssls.SslsTransformationService;
import org.eclipse.set.feature.table.pt1.sslw.SslwTransformationService;
import org.eclipse.set.feature.table.pt1.sslz.SslzTransformationService;
import org.eclipse.set.feature.table.pt1.ssvu.SsvuTransformationService;
import org.eclipse.set.feature.table.pt1.ssza.SszaTransformationService;
import org.eclipse.set.feature.table.pt1.sszs.SszsTransformationService;
import org.eclipse.set.feature.table.pt1.sszw.SszwTransformationService;
import org.eclipse.set.feature.table.pt1.sxxx.SxxxTransformationService;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.eclipse.set.unittest.utils.AbstractToolboxTest;
import org.eclipse.set.utils.graph.AsSplitTopGraph;
import org.jgrapht.graph.WeightedPseudograph;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * Abstract class for test transformation of Pt1 tables
 * 
 * @author Truong
 */
public class Pt1TableTest extends AbstractToolboxTest {
	private static EnumTranslation mockEnumTranlsation(final Object value) {
		return new EnumTranslation() {

			@Override
			public String getAlternative() {
				return value.toString();
			}

			@Override
			public String getKeyBasis() {
				return value.toString();
			}

			@Override
			public String getPresentation() {
				return value.toString();
			}

			@Override
			public String getSorting() {
				return "";
			}
		};
	}

	protected static Field getSuperClassField(final Class<?> clazz,
			final String fieldName) {
		Field result = null;
		Class<?> superClazz = clazz;
		while (result == null && superClazz != null) {
			try {
				final Field declaredField = superClazz
						.getDeclaredField(fieldName);
				result = declaredField;
			} catch (final Exception e) {
				superClazz = superClazz.getSuperclass();
				continue;
			}
		}
		return result;
	}

	protected static void mockServiceFieldVariable(
			final PlanPro2TableTransformationService service,
			final Object fieldVariable) throws Exception {
		for (final Field field : service.getClass().getDeclaredFields()) {
			if (field.getType().isInstance(fieldVariable)) {
				FieldUtils.writeField(field, service, fieldVariable, true);
			}
		}
	}

	protected static void mockTableNameInfo(
			final PlanPro2TableTransformationService service) {
		final String packageName = service.getClass().getPackageName();
		String tableName = packageName
				.substring(packageName.lastIndexOf(".") + 1);
		if (tableName.equals("dm")) {
			tableName = "sskp_dm";
		}
		final TableNameInfo mockTableNameInfo = Mockito
				.mock(TableNameInfo.class);
		Mockito.doReturn(mockTableNameInfo).when(service).getTableNameInfo();
		Mockito.when(mockTableNameInfo.getShortName()).thenReturn(tableName);

	}

	private BankServiceImpl bankService;
	private CacheService cacheService;

	private GeoKanteGeometryService geometryService;

	private Messages messages;

	private EnumTranslationService mockEnumTranslation;
	private TopologicalGraphServiceImpl topologicalGraphService;

	List<? extends AbstractPlanPro2TableTransformationService> transformationServices;

	protected List<MultiContainer_AttributeGroup> getLSTContainer() {
		return List
				.of(ContainerType.FINAL, ContainerType.INITIAL,
						ContainerType.SINGLE)
				.stream()
				.map(containerType -> PlanProSchnittstelleExtensions
						.getContainer(planProSchnittstelle, containerType))
				.filter(Objects::nonNull)
				.toList();
	}

	/**
	 * IMPROVE: use OSGi service unit test to inject these service
	 */
	protected void givenTransformationService() {
		transformationServices = List
				.of(new SsbbTransformationService(),
						new SsitTransformationService(),
						new SskaTransformationService(),
						new SskfTransformationService(),
						new SskgTransformationService(),
						new SskoTransformationService(),
						new SskpTransformationService(),
						new SskpDmTransformationService(),
						new SsksTransformationService(),
						new SsktTransformationService(),
						new SskwTransformationService(),
						new SskxTransformationService(),
						new SskzTransformationService(),
						new SslaTransformationService(),
						new SslbTransformationService(),
						new SsldTransformationService(),
						new SslfTransformationService(),
						new SsliTransformationService(),
						new SslnTransformationService(),
						new SslrTransformationService(),
						new SslsTransformationService(),
						new SslwTransformationService(),
						new SslzTransformationService(),
						new SsvuTransformationService(),
						new SszaTransformationService(),
						new SszsTransformationService(),
						new SszwTransformationService(),
						new SxxxTransformationService())
				.stream()
				.map(Mockito::spy)
				.toList();
	}

	protected void setupBankingService() throws Exception {
		if (topologicalGraphService == null) {
			setupTopGraphService();
		}
		bankService = new BankServiceImpl();
		final Method declaredMethod = bankService.getClass()
				.getDeclaredMethod("addBankingForContainer",
						BankingInformationSession.class,
						MultiContainer_AttributeGroup.class);
		declaredMethod.setAccessible(true);
		final BankingInformationSession bankingInformationSession = new BankingInformationSession();
		for (final MultiContainer_AttributeGroup container : getLSTContainer()) {
			declaredMethod.invoke(bankService, bankingInformationSession,
					container);
		}
	}

	protected void setupEnumTranslationService() {
		mockEnumTranslation = Mockito.mock(EnumTranslationService.class);
		Mockito.when(mockEnumTranslation.translate(any(), any()))
				.thenAnswer(invocation -> {
					return mockEnumTranlsation(invocation.getArguments()[1]);
				});
		Mockito.when(mockEnumTranslation.translate(anyBoolean()))
				.thenAnswer(invocation -> mockEnumTranlsation(
						invocation.getArgument(0)));
		Mockito.when(mockEnumTranslation.translate(any(Enumerator.class)))
				.thenAnswer(invocation -> mockEnumTranlsation(
						invocation.getArgument(0)));
		Mockito.when(mockEnumTranslation.translate(any(Translateable.class)))
				.thenAnswer(invocation -> mockEnumTranlsation(
						invocation.getArgument(0)));
	}

	protected void setupGeometryService() throws Exception {
		geometryService = new GeoKanteGeometryServiceImpl();
		final Map<PlanPro_Schnittstelle, GeoKanteGeometrySessionData> sessionesData = new HashMap<>();

		final GeoKanteGeometrySessionData geometrySessionData = new GeoKanteGeometrySessionData();
		sessionesData.put(planProSchnittstelle, geometrySessionData);

		FieldUtils.writeField(geometryService, "sessionesData", sessionesData,
				true);

		final Method declaredMethod = geometryService.getClass()
				.getDeclaredMethod("findGeoKanteGeometry",
						GeoKanteGeometrySessionData.class,
						MultiContainer_AttributeGroup.class);
		declaredMethod.setAccessible(true);

		declaredMethod.invoke(geometryService, geometrySessionData,
				PlanProSchnittstelleExtensions.getContainer(
						planProSchnittstelle, ContainerType.INITIAL));
		declaredMethod.invoke(geometryService, geometrySessionData,
				PlanProSchnittstelleExtensions.getContainer(
						planProSchnittstelle, ContainerType.FINAL));
		FieldUtils.writeField(geometryService, "isProcessComplete",
				Boolean.TRUE, true);

	}

	protected void setupMessages() throws Exception {
		messages = new Messages();
		for (final Field field : messages.getClass().getDeclaredFields()) {
			FieldUtils.writeField(field, messages, field.getName());
		}

	}

	protected void setupMockServices(
			final MockedStatic<Services> mockServices) {
		mockServices.when(Services::getCacheService).thenReturn(cacheService);
		mockServices.when(Services::getTopGraphService)
				.thenReturn(topologicalGraphService);
		mockServices.when(Services::getEnumTranslationService)
				.thenReturn(mockEnumTranslation);
		mockServices.when(Services::getGeometryService)
				.thenReturn(geometryService);
	}

	protected void setupTopGraphService() throws Exception {
		if (topologicalGraphService != null) {
			return;
		}
		topologicalGraphService = new TopologicalGraphServiceImpl();
		final Method declaredMethod = topologicalGraphService.getClass()
				.getDeclaredMethod("addContainerToGraph",
						WeightedPseudograph.class,
						MultiContainer_AttributeGroup.class);
		declaredMethod.setAccessible(true);
		final WeightedPseudograph<AsSplitTopGraph.Node, AsSplitTopGraph.Edge> weightedPseudograph = new WeightedPseudograph<>(
				AsSplitTopGraph.Edge.class);
		final List<MultiContainer_AttributeGroup> containers = getLSTContainer();
		for (final MultiContainer_AttributeGroup contanier : containers) {
			declaredMethod.invoke(topologicalGraphService, weightedPseudograph,
					contanier);
		}
		final Map<PlanPro_Schnittstelle, WeightedPseudograph<AsSplitTopGraph.Node, AsSplitTopGraph.Edge>> topGraphBaseMap = new HashMap<>();
		topGraphBaseMap.put(planProSchnittstelle, weightedPseudograph);
		FieldUtils.writeField(topologicalGraphService, "topGraphBaseMap",
				topGraphBaseMap, true);
	}

	protected void setupTransformationService() throws Exception {
		setupTopGraphService();
		setupBankingService();
		setupEnumTranslationService();
		setupMessages();
		setupGeometryService();
		givenCacheService();
		givenTransformationService();
		for (final AbstractPlanPro2TableTransformationService impl : transformationServices) {
			mockServiceFieldVariable(impl, topologicalGraphService);
			mockServiceFieldVariable(impl, messages);
			mockServiceFieldVariable(impl, bankService);
			mockServiceFieldVariable(impl, mockEnumTranslation);
			mockTableNameInfo(impl);
		}
	}

	void givenCacheService() {
		cacheService = new CacheServiceImpl() {
			@Override
			protected ToolboxFileRole getSessionRole(
					final PlanPro_Schnittstelle schnittStelle) {
				return ToolboxFileRole.SESSION;
			}
		};
	}

}