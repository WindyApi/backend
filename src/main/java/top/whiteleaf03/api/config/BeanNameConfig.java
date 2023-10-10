package top.whiteleaf03.api.config;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.util.StringUtils;

/**
 * @author WhiteLeaf03
 */
public class BeanNameConfig extends AnnotationBeanNameGenerator {
    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {

        //如果有设置了value，则用value，如果没有则是用全类名
        if (definition instanceof AnnotatedBeanDefinition) {
            String beanName = determineBeanNameFromAnnotation((AnnotatedBeanDefinition) definition);
            if (!StringUtils.hasText(beanName)) {
                //全限定类名
                beanName = definition.getBeanClassName();
            }
            return beanName;
        }

        // 使用默认类名
        return buildDefaultBeanName(definition, registry);
    }
}