# 😼 Advanced
⬇️ Advanced options waiting for you to discover are below. ⬇️

Do you need help? Join [our community](https://anbui.ovh/community/) now!

## 🔃 Upgrade tool
Upgrade or change the Qemu version you want to use without resetting the Bread VM using the Terminal.

QEMU 11.0.0:
```bash
apk add bash && curl -H 'Cache-Control: no-cache' -o setup.sh https://raw.githubusercontent.com/AnBui2004/Vectras-VM-Emu-Android/refs/heads/master/qemu/11.0.0/upgrade.sh && chmod +rwx setup.sh && ./setup.sh; rm setup.sh
```

QEMU 9.2.2 - 3dfx:
```bash
apk add bash && curl -H 'Cache-Control: no-cache' -o setup.sh https://raw.githubusercontent.com/AnBui2004/Vectras-VM-Emu-Android/refs/heads/master/qemu/9.2.2/upgrade.sh && chmod +rwx setup.sh && ./setup.sh; rm setup.sh
```

QEMU 7.2.22 - 3dfx:
```bash
apk add bash && curl -H 'Cache-Control: no-cache' -o setup.sh https://raw.githubusercontent.com/AnBui2004/Vectras-VM-Emu-Android/refs/heads/master/qemu/7.2.22/upgrade.sh && chmod +rwx setup.sh && ./setup.sh; rm setup.sh
```

## ⚙️ Bootstraps
QEMU 11.0.0:
- [For Android ARM (64-bit)](https://github.com/AnBui2004/Vectras-VM-Emu-Android/releases/download/4.0.8/base-vectras-vm-arm64-v8a.tar.gz)
- [See more at the Internet Archive](https://archive.org/details/qemu-11-0-0-for-vectras-vm-nbab)

QEMU 9.2.2 - 3dfx:
- [For Android ARM (64-bit)](https://github.com/AnBui2004/Vectras-VM-Emu-Android/releases/download/3.5.1/base-genegic-nosve-vectras-vm-arm64-v8a.tar.gz)
- [For Android x86 (64-bit)](https://github.com/AnBui2004/Vectras-VM-Emu-Android/releases/download/3.5.1/base-generic-vectras-vm-x86_64.tar.gz)
- [See them at the Internet Archive](https://archive.org/details/qemu-9-2-2-3dfx-for-vectras-vm-nbab)

QEMU 7.2.22 - 3dfx:
- [For Android ARM (64-bit)](https://github.com/AnBui2004/Vectras-VM-Emu-Android/releases/download/4.4.7/base-qemu-7.2.22-3dfx-july-2026-vectras-vm-arm64-v8a.tar.gz)
- [For Android ARM (32-bit)](https://github.com/AnBui2004/Vectras-VM-Emu-Android/releases/download/4.4.7/base-qemu-7.2.22-july-2026-vectras-vm-armeabi-v7a.tar.gz)
- [For Android x86 (64-bit)](https://github.com/AnBui2004/Vectras-VM-Emu-Android/releases/download/4.4.7/base-qemu-7.2.22-3dfx-july-2026-vectras-vm-x86_64.tar.gz)
- [For Android x86 (32-bit)](https://github.com/AnBui2004/Vectras-VM-Emu-Android/releases/download/4.4.7/base-qemu-7.2.22-july-2026-vectras-vm-x86.tar.gz)
- [See more at the Internet Archive (64-bit)](https://archive.org/details/qemu-7-2-22-for-vectras-vm-nbab)
- [See more at the Internet Archive (32-bit)](https://archive.org/details/qemu-7-2-22-for-vectras-vm-nbab0)

## 💽 3Dfx Wrappers

### New:
- [For i686](https://github.com/AnBui2004/Vectras-VM-Emu-Android/blob/master/3dfx/4.1.1%2B/3dfx-wrappers-i686.iso)
- [For i586](https://github.com/AnBui2004/Vectras-VM-Emu-Android/blob/master/3dfx/4.1.1%2B/3dfx-wrappers-i586.iso)

### Old:
- [For QEMU 9.2.x - 3dfx](https://github.com/AnBui2004/Vectras-VM-Emu-Android/blob/master/3dfx/old/3dfx-wrappers-3.5.0.iso)
- [For QEMU 8.2.0 - 3dfx](https://github.com/AnBui2004/Vectras-VM-Emu-Android/blob/master/3dfx/old/3dfx-wrappers-2.9.5.iso)

## 🔬 For developers
[Build libXlorie.so](termux-x11/README.md)