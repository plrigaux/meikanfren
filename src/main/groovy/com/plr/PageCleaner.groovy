package com.plr

import java.util.Map;

import groovy.util.slurpersupport.NodeChild

class PageCleaner extends GenOutput implements CleanerInt {

	def final keysToRemove = ["id", "href", "shape"]

	@Override
	public void clean(NodeChild subNode, TransType type) {

		if (subNode == null) {
			return;
		}

		subNode.'**'.find {
			it.name() == 'h1' && it.@class == 'TitrePage'
		}.each {
			// remove <h1 class="TitrePage">Traduction de ****</h1>

			if (it.@class == 'TitrePage') {
				it.replaceNode {}
			} else {
				it.replaceNode {
					h2(it.attributes(), it.text() )
				}
			}
		}

		subNode.'**'.findAll {
			it.name() == 'br'
		}.each {
			it.attributes().clear()
		}

		subNode.'**'.findAll {
			it.name() == 'a'
		}.each {

			String txt = it.text().trim().toLowerCase()

			//println "txt '" + txt + "'"

			switch ( txt ) {
				case "conjugaison":
				case "":
				case "&nbsp;":
				case " ":
					it.replaceNode {

					}
					break
				default:

					def newMap = it.attributes().findAll({!keysToRemove.contains(it.key)})
					it.replaceNode {
						span(newMap, it.text() )
					}
			}
		}

		def anodes = subNode.'**'.findAll {
			it.attributes().containsKey("lang")
		}.each {
			Map<String, String> attrs = it.attributes()

			//println attrs.keySet()
			String lang = attrs["lang"]
			attrs.remove("{http://www.w3.org/XML/1998/namespace}lang")

			//transfor the FRA to fr and ANG to en
			attrs["lang"] = lang.toLowerCase().startsWith("fr") ? "fr" : "en"

		}
	}
	
	String getTitle(Map <String, Object> params) {
		'Traduction : ' + params['word'] 
	}

	String getHeader(Map <String, Object> params) {
		'Traduction : ' + params['word']
	}
}
