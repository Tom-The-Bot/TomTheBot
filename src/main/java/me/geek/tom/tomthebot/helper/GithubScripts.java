package me.geek.tom.tomthebot.helper;

import org.apache.commons.io.IOUtils;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URLClassLoader;
import java.util.List;
import java.util.stream.Collectors;

public class GithubScripts {

    private static final String REPO_NAME = "Geek202/TomTheBotCommands";

    private static GitHub gitHub = null;

    static {
        GitHubBuilder builder = new GitHubBuilder(); // The token that was here has also been revoked lol.
        try {
            gitHub = builder.build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getScript(String name) throws IOException {
        return IOUtils.toString(getRepo().getFileContent("commands/"+name+".txt").read());
    }

    public static List<String> listScripts(String serverId) throws IOException {
        List<GHContent> files = getRepo()
                .getDirectoryContent("commands");
        List<String> ret = files.stream()
                .filter((GHContent::isFile))
                .map(f->f.getName().replace(".txt", ""))
                .collect(Collectors.toList());
        if (files.stream().anyMatch(d -> d.isDirectory() && d.getName().equals(serverId))) {
            ret.addAll(getRepo().getDirectoryContent("commands/"+serverId)
                    .stream()
                    .filter(GHContent::isFile)
                    .map(f -> f.getName().replace(".txt", ""))
                    .collect(Collectors.toList()));
        }

        return ret;
    }

    private static GHRepository getRepo() throws IOException {
        return gitHub.getRepository(REPO_NAME);
    }

}
