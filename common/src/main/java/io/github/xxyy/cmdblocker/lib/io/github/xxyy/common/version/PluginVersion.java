package io.github.xxyy.cmdblocker.lib.io.github.xxyy.common.version;

import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.JarInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Holds the loaded version of a Plugin, as given in its JAR's Implementation-Title, Implementation-Version and Implementation-Build.
 * This class is taken from xxyy's XYC with special permission from the author.
 *
 * @author xxyy98 (http://xxyy.github.io/)
 * @since 15.7.2014 // 1.3.0
 */
public final class PluginVersion {

    private final String implementationTitle;
    private final String implementationVersion;
    private final String implementationBuild;

    private PluginVersion(String implementationTitle, String implementationVersion, String implementationBuild) {
        this.implementationTitle = implementationTitle;
        this.implementationVersion = implementationVersion;
        this.implementationBuild = implementationBuild;
    }

    public static PluginVersion ofClass(Class<?> clazz) {
        final CodeSource codeSource = clazz.getProtectionDomain().getCodeSource();
        final URL url = codeSource.getLocation();

        if (url.toExternalForm().endsWith("jar")) { //Account for .xyjar
            try (JarInputStream jis = new JarInputStream(url.openStream())) {
                final Attributes attrs = jis.getManifest().getMainAttributes();

                return new PluginVersion(
                        attrs.getValue(Name.IMPLEMENTATION_TITLE),
                        attrs.getValue(Name.IMPLEMENTATION_VERSION),
                        attrs.getValue("Implementation-Build"));
            } catch (IOException ex) {
                Logger.getLogger(PluginVersion.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    @Override
    public String toString(){
        return implementationTitle + " Version " + implementationVersion + " Build " + implementationBuild;
    }

    public String getImplementationTitle() {
        return this.implementationTitle;
    }

    public String getImplementationVersion() {
        return this.implementationVersion;
    }

    public String getImplementationBuild() {
        return this.implementationBuild;
    }
}
