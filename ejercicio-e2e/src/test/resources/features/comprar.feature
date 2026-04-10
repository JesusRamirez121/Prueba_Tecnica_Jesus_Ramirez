# language: es
Característica: Flujo de compra en OpenCart
  Como usuario no registrado en la tienda OpenCart
  Quiero agregar dos productos al carrito y completar la compra como invitado
  Para finalizar mi pedido exitosamente sin necesidad de crear una cuenta

  Escenario: Compra exitosa de dos productos como usuario invitado
    Dado que el usuario está en la página principal de OpenCart
    Cuando el usuario agrega el producto "MacBook" al carrito de compras
    Y el usuario agrega el producto "iPhone" al carrito de compras
    Y visualiza el carrito de compras
    Entonces el carrito muestra los productos agregados
    Cuando procede al proceso de checkout
    Y elige continuar como invitado
    Y completa los datos de facturación del invitado
    Y continúa con los detalles de entrega
    Y selecciona el método de envío disponible
    Y selecciona el método de pago y acepta los términos
    Y confirma el pedido
    Entonces debe ver el mensaje "Your order has been placed!"