using Avalonia.Controls;
using Avalonia.Markup.Xaml;
using System.Runtime.InteropServices;

namespace windowedchatclient.Views.TitleBars
{
    public class WindowsTitleBar : UserControl
    {
        public WindowsTitleBar()
        {
            InitializeComponent();

            if (!RuntimeInformation.IsOSPlatform(OSPlatform.Windows)) IsVisible = false;
            else
            {
                Button minimizeButton = this.FindControl<Button>("MinimizeButton");
                minimizeButton.Click += MinimizeWindow!;
                
                Button settingsButton = this.FindControl<Button>("SettingsButton");
                settingsButton.Click += OpenSettings!;

#if DEBUG
                Image windowIcon = this.FindControl<Image>("WindowIcon");
                windowIcon.DoubleTapped += CloseWindow!;
#endif
            }
        }

        private void CloseWindow(object sender, Avalonia.Interactivity.RoutedEventArgs e)
        {
            Window hostWindow = (Window) VisualRoot;
            hostWindow.Close();
        }

        private void MinimizeWindow(object sender, Avalonia.Interactivity.RoutedEventArgs e)
        {
            Window hostWindow = (Window) VisualRoot;
            hostWindow.WindowState = WindowState.Minimized;
        }

        private void OpenSettings(object sender, Avalonia.Interactivity.RoutedEventArgs e)
        {
            // todo: add settings popup
        }

        private void InitializeComponent()
        {
            AvaloniaXamlLoader.Load(this);
        }
    }
}
