extends Node2D

# TODO: Update to match your plugin's name
var _plugin_name: String = "GodotAndroidPluginTemplate"
var _android_plugin: Variant

func _ready() -> void:
	if Engine.has_singleton(_plugin_name):
		_android_plugin = Engine.get_singleton(_plugin_name)
	else:
		printerr("Couldn't find plugin " + _plugin_name)

func _on_Button_pressed() -> void:
	if _android_plugin:
		# TODO: Update to match your plugin's API
		_android_plugin.helloWorld()
