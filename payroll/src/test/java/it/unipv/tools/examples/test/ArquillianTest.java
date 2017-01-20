package it.unipv.tools.examples.test;


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
import org.primefaces.event.AbstractAjaxBehaviorEvent;
import org.primefaces.event.SelectEvent;

import it.unipv.view.utils.CalendarView;

public class ArquillianTest {

	Logger log = Logger.getLogger(ArquillianTest.class);
	
	@Deployment(name = "payrolltest")
	@OverProtocol("Servlet 3.0") // Evita il timeout sui test lunghi eseguiti da Eclipse
	public static Archive<?> createDeployment() {
		 File[] files = Maven.resolver().loadPomFromFile("pom.xml")
		            .importRuntimeDependencies().resolve().withTransitivity().asFile();

		WebArchive archive = ShrinkWrap.create(WebArchive.class, "payrolltest.war")
        .addPackages(true, "it.unipv.controller")
        .addPackages(true, "it.unipv.dao")
        .addPackages(true, "it.unipv.model")
        .addPackages(true, "it.unipv.model.admin")
        .addPackages(true, "it.unipv.model.employees")
        .addPackages(true, "it.unipv.model.payment")
        .addPackages(true, "it.unipv.model.salesreceipt")
        .addPackages(true, "it.unipv.model.timecard")
        .addPackages(true, "it.unipv.model.union")
        .addPackages(true, "it.unipv.view.employees")
        .addPackages(true, "it.unipv.view.login")
        .addPackages(true, "it.unipv.view.post")
        .addPackages(true, "it.unipv.view.registration")
        .addPackages(true, "it.unipv.view.utils")
        .addPackages(true, "it.unipv.view.payroll")
        .addPackages(true, "it.unipv.utils.payrollalgorithm")
        .addPackages(true, "it.unipv.utils.payrollalgorithm.filter")
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
	
