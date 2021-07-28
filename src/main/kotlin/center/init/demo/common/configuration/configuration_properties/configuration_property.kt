package center.init.demo.common.configuration.configuration_properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "demo")
data class AppConfigurationProperties(
    val `controller-package`: String,
)
