package ramos.adriano.raul.buscadorgithub

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat

class ConfiguracoesFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.pref_github)
    }
}