package org.jboss.tools.examples.test;


import java.io.File;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.logging.Logger;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.view.utils.CalendarView;
import org.primefaces.event.AbstractAjaxBehaviorEvent;
import org.primefaces.event.SelectEvent;

public class ArquillianTest {

	Logger log = Logger.getLogger(ArquillianTest.class);
	
	@Deployment(name = "payrolltest")
	@OverProtocol("Servlet 3.0") // Evita il timeout sui test lunghi eseguiti da Eclipse
	public static Archive<?> createDeployment() {
		 File[] files = Maven.resolver().loadPomFromFile("pom.xml")
		            .importRuntimeDependencies().resolve().withTransitivity().asFile();

		WebArchive archive = ShrinkWrap.create(WebArchive.class, "payrolltest.war")
        .addPackages(true, "org.jboss.controller")
        .addPackages(true, "org.jboss.dao")
        .addPackages(true, "org.jboss.model")
        .addPackages(true, "org.jboss.model.admin")
        .addPackages(true, "org.jboss.model.employees")
        .addPackages(true, "org.jboss.model.payment")
        .addPackages(true, "org.jboss.model.salesreceipt")
        .addPackages(true, "org.jboss.model.timecard")
        .addPackages(true, "org.jboss.model.union")
        .addPackages(true, "org.jboss.view.employees")
        .addPackages(true, "org.jboss.view.login")
        .addPackages(true, "org.jboss.view.post")
        .addPackages(true, "org.jboss.view.registration")
        .addPackages(true, "org.jboss.view.utils")
        .addClass(CalendarView.class)
        .addClass(SelectEvent.class)
        .addClass(AbstractAjaxBehaviorEvent.class)
        .addAsLibraries(files)
        .addClass(ArquillianTest.class)

        .addAsResource("META-INF/persistence.xml")
        /*.addAsResource("META-INF/ejb-jar.xml")*/
        /*.addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml").resolve("org.apache.httpcomponents:httpmime").withTransitivity().asFile())*/
        .addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
		
		// Esportazione di prova per controllo
		  archive.as(ZipExporter.class).exportTo(
				    new File("target/arquillianPackage.war"), true);
		 
		
		return archive;
		
	}
	
	
}
	
