
// eagerly import theme styles so as we can override them
import "@vaadin/vaadin-lumo-styles/all-imports";
import "@vaadin/vaadin-charts/theme/vaadin-chart-default-theme";
        const $_documentContainer = document.createElement('template');
        $_documentContainer.innerHTML = `
<custom-style>
  <style>
    html {
          --lumo-font-size: 1rem;
          --lumo-font-size-xxxl: 1.75rem;
          --lumo-font-size-xxl: 1.375rem;
          --lumo-font-size-xl: 1.125rem;
          --lumo-font-size-l: 1rem;
          --lumo-font-size-m: 0.875rem;
          --lumo-font-size-s: 0.8125rem;
          --lumo-font-size-xs: 0.75rem;
          --lumo-font-size-xxs: 0.6875rem;
          --lumo-size-xl: 3rem;
          --lumo-size-l: 2.5rem;
          --lumo-size-m: 2rem;
          --lumo-size-s: 1.75rem;
          --lumo-size-xs: 1.5rem;
          --lumo-space-xl: 1.875rem;
          --lumo-space-l: 1.25rem;
          --lumo-space-m: 0.625rem;
          --lumo-space-s: 0.3125rem;
          --lumo-space-xs: 0.1875rem;
        }
  </style>
</custom-style>


<custom-style>
  <style>
    html {
      overflow:hidden;
    }
    vaadin-app-layout vaadin-tab a:hover {
      text-decoration: none;
    }

    .router-link {
        flex-direction: column;
        align-items: start;
        margin-top: -0.25rem;
        padding-top: 0.25rem;
    }

    .router-link iron-icon {
        width: 24px;
        height: 24px;
    }
    
    .router-link span {
        font-size: 16px;
        margin-left: 25px;
        margin-top: 2px;
    }

    .top-bar img {
        margin: 10px 10px 10px 35px;
    }

    .top-bar h2 {
        margin-top: 20px;
    }

    .top-bar vaadin-button {
        margin-bottom: 15px;
        margin-right: 35px;
    }

  </style>
</custom-style>

<dom-module id="app-layout-theme" theme-for="vaadin-app-layout">
  <template>
    <style>
      [part="navbar"] {
        align-items: center;
        justify-content: center;
      }
    </style>
  </template>
</dom-module>
`;
        document.head.appendChild($_documentContainer.content);
