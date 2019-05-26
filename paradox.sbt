
//sourceDirectory in Paradox := sourceDirectory.value / "main" / "paradox"

enablePlugins(ParadoxMaterialThemePlugin)

enablePlugins(ParadoxSitePlugin)

Compile / paradoxMaterialTheme ~= {
  _.withRepository(uri("https://github.com/sfxcode/sapphire-core"))
}

// enablePlugins(SiteScaladocPlugin)

enablePlugins(GhpagesPlugin)

git.remoteRepo := "git@github.com:sfxcode/sapphire-core.git"
ghpagesNoJekyll := true

