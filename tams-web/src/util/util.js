export default {
  rgb2hex (r, g, b) {
    const hex = r << 16 | g << 8 | b
    return '#' + hex.toString(16)
  }
}
