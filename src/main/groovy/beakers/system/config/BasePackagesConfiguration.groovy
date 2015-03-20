package beakers.system.config

import beakers.system.BeakersCore
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.BeanFactoryAware
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.boot.autoconfigure.AutoConfigurationPackages
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar
import org.springframework.core.type.AnnotationMetadata

import java.lang.reflect.Field

/**
 * Fix for AutoConfigurationPackages.
 * Adds all modules base packages to bean AutoConfigurationPackages.BasePackages.
 */
@Configuration
class BasePackagesConfiguration implements BeanFactoryAware, ImportBeanDefinitionRegistrar {

    BeanFactory beanFactory

    @Override
    void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AutoConfigurationPackages.get(beanFactory)
        def bean = beanFactory.getBean(AutoConfigurationPackages.class.name)

        Field f = bean.class.getDeclaredField("packages")
        f.accessible = true
        List list = (List) f.get(bean)
        list.addAll(BeakersCore.activeModules.collect { it.package.name })
    }
}
