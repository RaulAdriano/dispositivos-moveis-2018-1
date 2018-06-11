package ramos.adriano.raul.buscadorgithub

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.preference.ListPreference
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat

class ConfiguracoesFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {

        val preference = findPreference(key)
        if(preference != null){
            atualizarPreferencesSummary(preference)
        }

    }

    fun atualizarPreferencesSummary(preferences: Preference){
        if(preferences is ListPreference){
            val corSelecionada = preferenceScreen.sharedPreferences.getString(preferences.key,"")
            val indexSelecionado = preferences.findIndexOfValue(corSelecionada)
            val tituloSelecionado = preferences.entries[indexSelecionado]
            preferences.summary == tituloSelecionado
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.pref_github)

        for (i in 0 until preferenceScreen.preferenceCount){
            val preference = preferenceScreen.getPreference(i)
            atualizarPreferencesSummary(preference)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

}