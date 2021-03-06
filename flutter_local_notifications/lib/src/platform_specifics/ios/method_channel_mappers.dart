import 'initialization_settings.dart';
import 'notification_attachment.dart';
import 'notification_details.dart';

// ignore_for_file: public_member_api_docs

extension IOSNotificationActionMapper on IOSNotificationAction {
  Map<String, Object> toMap() => <String, Object>{
    'identifier': identifier,
    'title': title,
    'options': options.map((e)=>e.index+1).toList()// ignore: always_specify_types
  };
}

extension IOSNotificationCategoryMapper on IOSNotificationCategory {
  Map<String, Object> toMap() => <String, Object>{
    'identifier': identifier,
    'actions': actions.map((e)=>e.toMap()).toList(),// ignore: always_specify_types
    'options': options.map((e)=>e.index+1).toList() // ignore: always_specify_types
  };
}

extension IOSInitializationSettingsMapper on IOSInitializationSettings {
  Map<String, Object> toMap() => <String, Object>{
        'requestAlertPermission': requestAlertPermission,
        'requestSoundPermission': requestSoundPermission,
        'requestBadgePermission': requestBadgePermission,
        'defaultPresentAlert': defaultPresentAlert,
        'defaultPresentSound': defaultPresentSound,
        'defaultPresentBadge': defaultPresentBadge,
        'notificationCategories': notificationCategories?.map((e)=>e.toMap())?.toList() // ignore: always_specify_types
      };
}

extension IOSNotificationAttachmentMapper on IOSNotificationAttachment {
  Map<String, Object> toMap() => <String, Object>{
        'identifier': identifier ?? '',
        'filePath': filePath,
      };
}

extension IOSNotificationDetailsMapper on IOSNotificationDetails {
  Map<String, Object> toMap() => <String, Object>{
        'presentAlert': presentAlert,
        'presentSound': presentSound,
        'presentBadge': presentBadge,
        'subtitle': subtitle,
        'sound': sound,
        'badgeNumber': badgeNumber,
        'attachments': attachments
            ?.map((a) => a.toMap()) // ignore: always_specify_types
            ?.toList(),
        'categoryIdentifier': categoryIdentifier,
        'threadIdentifier': threadIdentifier
      };
}
