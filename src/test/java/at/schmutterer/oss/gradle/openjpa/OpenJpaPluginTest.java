package at.schmutterer.oss.gradle.openjpa;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Collections;
import java.util.Set;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.TaskContainer;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import groovy.lang.Closure;


public class OpenJpaPluginTest {
	@Test
	public void test() {
		OpenJpaPlugin plugin = new OpenJpaPlugin();
		
		Project project = mock(Project.class);
		ExtensionContainer ext = mock(ExtensionContainer.class);
		TaskContainer tasks = mock(TaskContainer.class);
		ConfigurationContainer configs = mock(ConfigurationContainer.class);
		Configuration config = mock(Configuration.class);
		Task task = mock(Task.class);
		Task classes = mock(Task.class);
		
		when(project.getExtensions()).thenReturn(ext);
		when(project.getTasks()).thenReturn(tasks);
		when(project.getConfigurations()).thenReturn(configs);
		when(configs.getAt("compile")).thenReturn(config);
		Set<File> emptySet = Collections.emptySet();
		when(config.getFiles()).thenReturn(emptySet);
		
	    /*    def resourceUrls = target.sourceSets.main.resources.srcDirs.collect {
	            return target.file(it).toURI().toURL()
	        }
	        def classesDir = target.sourceSets.main.output.classesDir
	        return compileClassPathURLs + resourceUrls + classesDir.toURI().toURL()*/
		
		when(tasks.create("openjpaEnhance")).thenReturn(task);
		when(tasks.getAt("classes")).thenReturn(classes);
		
		Closure<?> closure = anyObject();
		
		when(task.doLast(closure)).thenAnswer(new Answer<Task>() {
		     @Override
			public Task answer(InvocationOnMock invocation) {
		         Object[] args = invocation.getArguments();
		         //Object mock = invocation.getMock();
		         ((Closure<?>)args[0]).call();
		         //return "called with arguments: " + Arrays.toString(args);
		         return (Task) invocation.getMock();
		     }
		 });
        /*def task = target.tasks.create("openjpaEnhance").doLast {
            URL[] urls = collectURLs(target)
            def oldClassLoader = Thread.currentThread().getContextClassLoader()
            def loader = new URLClassLoader(urls, oldClassLoader)
            try {
                Thread.currentThread().setContextClassLoader(loader)
                FileCollection tree = target.openjpa.files;
                if (tree == null) {
                    tree = target.fileTree(target.sourceSets.main.output.classesDir)
                }
                logger.info("enhancing {}", tree.files);
                PCEnhancer.run(
                        tree.files as String[],
                        new Options(target.openjpa.toProperties())
                )
            } finally {
                Thread.currentThread().setContextClassLoader(oldClassLoader)
            }
        }*/

        plugin.apply(project);
        
        //assertEquals(1, task.getActions().size());
	}
}
