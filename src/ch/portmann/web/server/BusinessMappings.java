package ch.portmann.web.server;

import com.google.inject.AbstractModule;

/**
 * Bind abstract classes to concrete classes
 */
public class BusinessMappings extends AbstractModule {

    private String file;

    @Override
    protected void configure() {

        /*
         * Bindings
         */

        //  bind(TaskSearchEngine.class).to(TaskSearchEngineImpl.class);
        //  bind(TaskStore.class).to(TaskStoreProxy.class);

    }
}
