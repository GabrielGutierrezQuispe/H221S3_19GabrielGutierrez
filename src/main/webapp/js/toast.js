const toast = document.getElementById("toasts-messaje");

const messages = [
  "Notificación Informativa",
  "Notificación Error",
  "Notificación Exitosa",
  "Notificación Advertencia"
];

const types = ["info", "success", "error", "warning"];

function createToast(type = null, message = null) {
  console.log('Motrando Toast');
  let properties;

  const notif = document.createElement("div");
  const notifIcon = document.createElement("span");
  const notificationType = type ? type : getRandomType();

  properties = setProperties(notificationType);

  notif.classList.add("toast-content");
  notif.classList.add(notificationType);

  notifIcon.classList.add(properties[0]);
  notifIcon.classList.add(properties[1]);

  console.log('Message: ', message);
  notif.innerText = message ? message : messages[properties[2]];

  toast.appendChild(notif);
  notif.append(notifIcon);

  setTimeout(() => {
    notif.remove();
  }, 2500);
}

function setProperties(notificationType) {
  let propertyList;

  switch (notificationType) {
    case "info":
      propertyList = ["bx", "bx-info-circle", 0];
      break;
    case "error":
      propertyList = ["bx", "bxs-error-circle", 1];
      break;
    case "success":
      propertyList = ["bx", "bxs-check-circle", 2];
      break;
    case "warning":
      propertyList = ["bx", "bxs-error", 3];
      break;
  }

  return propertyList;
}

function getRandomType() {
  return types[Math.floor(Math.random() * types.length)];
}
